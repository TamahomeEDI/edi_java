package jp.co.edi_java.app.config.db.jtm;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@AnnotateWith(annotations = {
        @Annotation(target = AnnotationTarget.CLASS, type = Repository.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Autowired.class),
        @Annotation(target = AnnotationTarget.CONSTRUCTOR, type = Jtm.class) })
public @interface JtmConfigAutowireable {
}