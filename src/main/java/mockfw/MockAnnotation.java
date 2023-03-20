package mockfw;

import org.checkerframework.checker.initialization.qual.Initialized;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Initialized
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
public @interface MockAnnotation {
}
