package jp.co.edi_java.app.util.google;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import jp.co.edi_java.app.util.common.CommonUtils;
import jp.co.keepalive.springbootfw.exception.CoreRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GoogleDriveApi {

	private static final String APPLICATION_NAME = "電子発注システム";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    // 設定ファイルで上書き
    private static String TOKENS_DIRECTORY_PATH = "/home/web/edi_php/tmp/googledrive/tokens";
    private static String CREDENTIALS_FILE_PATH = "/credentials.json";

	private GoogleDriveApi( @Value("${googledrive.token.path}") String tokenPath,
			@Value("${googledrive.credentials.path}") String credentialsPath) {

		TOKENS_DIRECTORY_PATH = tokenPath;
		CREDENTIALS_FILE_PATH = credentialsPath;
	}

    /**
     * Google Driveを操作するためのserviceを取得する
     */
    public static Drive connectGoogleDrive() {
    	Drive service = null;
    	try {
    		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    		Credential credential = googleAuthorize(HTTP_TRANSPORT);
    		if (Objects.nonNull(credential)) {
    			service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
    					.setApplicationName(APPLICATION_NAME)
    					.build();
    		}
    	} catch (GeneralSecurityException e) {
    		throw new CoreRuntimeException(e.getMessage());
    	} catch (IOException e) {
    		throw new CoreRuntimeException(e.getMessage());
    	}
        return service;
    }

    /**
     * Google Drive上のファイルリストを取得
     * @return Map<String,Map<String,Object>> - {fileId:{id:String,name:String,parentIds:List<String>}}
     */
    public static Map<String,Map<String,Object>> getFileList(Drive service) {
    	Map<String,Map<String,Object>> ret = new HashMap<String,Map<String,Object>>();
    	FileList fileList = null;
    	if (Objects.isNull(service)) {
    		throw new CoreRuntimeException("google drive service is null. can not download file from google drive.");
    	} else {
    		try {
    			fileList = service.files().list().setFields("*").execute();
    			List<File> driveFiles = fileList.getFiles();
    			for (File file : driveFiles) {
    				if (!file.getTrashed()) {
    					Map<String, Object> map = new HashMap<String, Object>();
    					map.put("id", file.getId());
        				map.put("name", file.getName());
        				map.put("parentIds", file.getParents());
        				ret.put(file.getId(), map);
    				}
    			}
    		} catch (IOException e) {
    			throw new CoreRuntimeException(e.getMessage());
    		}
    	}
    	return ret;
    }

    /**
     * Google Drive上にフォルダを作成する
     */
    public static String createFolder(Drive service, String folderId, String folderName) {
    	String ret = null;
    	try {
    		// com.google.api.services.drive.model.File
    		File folder = new File();
    		folder.setName(folderName);
    		if (Objects.nonNull(folderId)) { // parent folder
				folder.setParents(Collections.singletonList(folderId));
			}
    		folder.setMimeType("application/vnd.google-apps.folder");

    		File createdFolder = service.files().create(folder).setFields("id").execute();
    		ret = createdFolder.getId();
    		log.info("Folder ID: " + ret);
    	} catch (IOException e) {
    		throw new CoreRuntimeException(e.getMessage());
    	}
        return ret;
    }

    /**
     * Google Drive上のファイルを指定パスへdownloadする
     */
    public static String downloadFile(Drive service, String fileId, String folderPath, String fileName) {
    	String ret = null;
    	if (Objects.isNull(service) || Objects.isNull(fileId) || Objects.isNull(folderPath) || Objects.isNull(fileName)) {
    		throw new CoreRuntimeException("google drive service or fileId or filePath(Name) is null. can not download file from google drive.");
    	} else {
    		java.io.File localFolderPath = new java.io.File(folderPath);
			if (!localFolderPath.exists()) {
				localFolderPath.mkdirs();
			}
			String filePath = "";
			String cookiefileName = fileId + "_cookies.txt";
			String cookiePath = "";
			String confirmfileName = fileId + "_confirm.txt";
			String confirmPath = "";
			if (Objects.equals(folderPath.substring(folderPath.length()-1),"/")) {
				filePath = folderPath + fileName;
				cookiePath = folderPath + cookiefileName;
				confirmPath = folderPath + confirmfileName;
			} else {
				filePath = folderPath + "/" + fileName;
				cookiePath = folderPath + "/" + cookiefileName;
				confirmPath = folderPath + "/" + confirmfileName;
			}
			try {
				GenericUrl downloadUrl = service.files().get(fileId).setAlt("media").buildHttpRequestUrl();
				File gdfile = service.files().get(fileId).setFields("id, name, size, webContentLink").execute();
				Long gdfileSize = gdfile.getSize();
				String webContentLink = gdfile.getWebContentLink();
				log.info(webContentLink);
				log.info(gdfileSize.toString());

				HttpRequest httpRequestGet = service.getRequestFactory().buildGetRequest(new GenericUrl(webContentLink));
				//分割ダウンロードはRangeを指定しexecute to inputstream, output bytearray を繰り返し実行する
				//httpRequestGet.getHeaders().setRange("bytes=" + 0 + "-" + (10*1024));
				HttpResponse response = httpRequestGet.execute();
				String authHeader = httpRequestGet.getHeaders().getAuthorization();
				response.disconnect();
				if (Objects.isNull(authHeader)) {
					log.info("authorization is null");
					return ret;
				}
				authHeader = "Authorization: " + authHeader;
				log.info(authHeader);
				String acceptHeader = "Accept: application/json";
				// wgetコマンド 25MB以上：ウィルススキャンエラー画面が取得される想定, 25MB未満：ログイン画面が取得される想定
				String[] wgetCommand1 = {"wget", "--header", authHeader, "--header", acceptHeader, "--quiet", "--save-cookies", cookiePath, "--keep-session-cookies", "--no-check-certificate", webContentLink , "-O", confirmfileName};
				long timeOut2m = 2 * 60;
				CommonUtils.processDone(wgetCommand1, localFolderPath, timeOut2m, null);

				java.io.File confirmFile = new java.io.File(confirmPath);
				if (confirmFile.exists() && confirmFile.length() > 0) {
					try (InputStream is = new FileInputStream(confirmFile)) {
						String content = IOUtils.toString(is);
						Pattern pattern = java.util.regex.Pattern.compile("confirm=[0-9A-Za-z_-]+");
						Matcher matcher = pattern.matcher(content);
						String nUrl = downloadUrl.toString();
						if (matcher.find()) {
							// ウィルススキャンエラー画面を取得した場合
							String confirmOpt = matcher.group();
							log.info("confirm: " + confirmOpt);
							nUrl += "&" + confirmOpt;
						}
						String[] wgetCommand2 = {"wget", "--header", authHeader, "--header", acceptHeader, "--load-cookies", cookiePath, nUrl, "-O", fileName};
						long timeOut10m = 10 * 60;
						// wgetコマンド ファイルダウンロード
						CommonUtils.processDone(wgetCommand2, localFolderPath, timeOut10m, null);
					} catch (FileNotFoundException e) {
						log.info(e.getMessage());
					}
				}
				java.io.File localFile = new java.io.File(filePath);
				if (localFile.exists() && localFile.length() > 0) {
					log.info("exists file: " + filePath);
					log.info("download fileId: " + fileId );
					ret = filePath;
				}

//	    		try (OutputStream filestream = new FileOutputStream(localFile)) {
// 通常のAPI
//    			//25MB以上のファイルはウィルススキャンに失敗するため、ダイアログ表示が邪魔をしてダウンロードエラーになる
//    			//Rangeを指定してbyte分割しても本体のファイルが大きいとスキャンエラーになる
//    			//service.files().get(fileId).executeMediaAndDownloadTo(filestream);
//
// Range追加やURLを変更するなどHttpHeaderを修正してアクセスするサンプル
//    			GenericUrl downloadUrl = service.files().get(fileId).setAlt("media").buildHttpRequestUrl();
//    			File gdfile = service.files().get(fileId).setFields("id, name, size, webContentLink").execute();
//    			Long gdfileSize = gdfile.getSize();
//    			String webContentLink = gdfile.getWebContentLink();
//    			log.info(webContentLink);
//    			log.info(gdfileSize.toString());
//
//    			HttpRequest httpRequestGet = service.getRequestFactory().buildGetRequest(new GenericUrl(webContentLink));
//    			//分割ダウンロードはRangeを指定しexecute to inputstream, output bytearray を繰り返し実行する
//    			//httpRequestGet.getHeaders().setRange("bytes=" + 0 + "-" + (10*1024));
//    			HttpResponse response = httpRequestGet.execute();
//				// 認証情報はexecute()後に取得可能
//    			String auth = httpRequestGet.getHeaders().getAuthorization();
//    			if (Objects.isNull(auth)) {
//    				log.info("authorization is null");
//    				return ret;
//    			}
//    			auth = "Authorization: " + auth;
//    			log.info(auth);
//				InputStream is = response.getContent();
//				byte [] receivedByteArray = IOUtils.toByteArray(is);
//				filestream.write(receivedByteArray);
//				response.disconnect();

    		} catch (IOException e) {
    			ret = null;
    			log.info(e.getMessage());
    			throw new UncheckedIOException(e);
    			//throw new CoreRuntimeException(e.getMessage());
    		}
    	}

    	return ret;
    }

    /**
     * Google Drive上にファイルを作成する
     * @param Drive service
     * @param String folderId
     */
    public static Map<String,String> uploadFile(Drive service, String folderId, String filePath, String fileName, String mimeType) {
    	Map<String,String> ret = null;

    	if (Objects.isNull(service) || Objects.isNull(filePath) || Objects.isNull(fileName) || Objects.isNull(mimeType)) {
    		throw new CoreRuntimeException("google drive service or filePath(Name) is null. can not upload file to google drive.");
    	} else {
    		try {
    			File fileMetadata = new File();
    			fileMetadata.setName(fileName);
    			if (Objects.nonNull(folderId)) {
    				fileMetadata.setParents(Collections.singletonList(folderId));
    			}

    			java.io.File localFilePath = new java.io.File(filePath);
    			FileContent mediaContent = new FileContent(mimeType, localFilePath);

    			File file = service.files().create(fileMetadata, mediaContent)
    					.setFields("id, name, webContentLink, webViewLink")
    					.execute();
    			/* Set readonly permission */
//    			setPermissionsToFileReadOnly(service, file.getId());

    			ret = new HashMap<String,String>();
    			ret.put("id",file.getId());
    			ret.put("name",file.getName());
    			ret.put("webContentLink",file.getWebContentLink());
    			ret.put("webViewLink",file.getWebViewLink());

    			log.info("File Name: " + file.getName());
    			log.info("File ID: " + file.getId());
    			log.info("Download Link: " + file.getWebContentLink());
    			log.info("View Link: " + file.getWebViewLink());
    		} catch (IOException e) {
    			throw new CoreRuntimeException(e.getMessage());
    		}
    	}
        return ret;
    }

    /**
     * Google Drive. ファイルのパーミッションをreadonlyに設定する
     */
//    public static void setPermissionsToFileReadOnly(Drive service, String fileId) {
//    	try {
//    		JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
//    			@Override
//    			public void onFailure(GoogleJsonError e,
//    					HttpHeaders responseHeaders)
//    							throws IOException {
//    				throw new CoreRuntimeException(e.getMessage());
//    			}
//
//    			@Override
//    			public void onSuccess(Permission permission,
//    					HttpHeaders responseHeaders)
//    							throws IOException {
//    				log.info("Permission ID: " + permission.getId());
//    			}
//    		};
//    		// anyoneユーザは読み取り専用に
//    		BatchRequest batch = service.batch();
//    		Permission userPermission = new Permission()
//    				.setType("anyone")
//    				.setRole("reader")
//    				.setAllowFileDiscovery(false);
//    		service.permissions().create(fileId, userPermission)
//    		.setFields("id")
//    		.queue(batch, callback);
//    		batch.execute();
//    	} catch (IOException e) {
//    		throw new CoreRuntimeException(e.getMessage());
//    	}
//    }

    /**
     * Google Drive APIを利用するために認証を行う
     */
    private static Credential googleAuthorize(final NetHttpTransport HTTP_TRANSPORT) {
    	Credential credential = null;
    	try {
    		// Load client secrets.
    		InputStream in = GoogleDriveApi.class.getResourceAsStream(CREDENTIALS_FILE_PATH);

    		if (in == null) {
    			throw new CoreRuntimeException("Resource not found: " + CREDENTIALS_FILE_PATH);
    		}
    		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    		java.io.File tokenFolder = new java.io.File(TOKENS_DIRECTORY_PATH);
    		if (!tokenFolder.exists()) {
    			tokenFolder.mkdirs();
    		}
    		// Build flow and trigger user authorization request.
    		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
    				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
    				.setDataStoreFactory(new FileDataStoreFactory(tokenFolder))
    				.setAccessType("offline")
    				.build();

    		//LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    		LocalServerReceiver receiver = new LocalServerReceiver();
    		credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    	} catch (IOException e) {
    		throw new CoreRuntimeException(e.getMessage());
    	}
        return credential;
    }
}


