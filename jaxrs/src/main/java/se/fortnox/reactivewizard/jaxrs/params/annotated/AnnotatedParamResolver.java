package se.fortnox.reactivewizard.jaxrs.params.annotated;

import se.fortnox.reactivewizard.jaxrs.FieldError;
import se.fortnox.reactivewizard.jaxrs.JaxRsRequest;
import se.fortnox.reactivewizard.jaxrs.WebException;
import se.fortnox.reactivewizard.jaxrs.params.ParamResolver;
import se.fortnox.reactivewizard.jaxrs.params.deserializing.Deserializer;
import se.fortnox.reactivewizard.jaxrs.params.deserializing.DeserializerException;
import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import javax.ws.rs.DefaultValue;

import static rx.Observable.just;

abstract class AnnotatedParamResolver<T> implements ParamResolver<T> {

    private final Deserializer<T> deserializer;
    protected final String paramName;
    private final String defaultValue;

    public AnnotatedParamResolver(Deserializer<T> deserializer, String paramName, DefaultValue defaultValueAnnotation) {
        this.deserializer = deserializer;
        this.paramName = paramName;
        this.defaultValue = defaultValueAnnotation != null ? defaultValueAnnotation.value() : null;
    }

    protected abstract String getValue(JaxRsRequest request);

    protected String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public Observable<T> resolve(JaxRsRequest request) {
        try {
            return just(deserializer.deserialize(getValue(request)));
        } catch (DeserializerException deserializerException) {
            throw new WebException(HttpResponseStatus.BAD_REQUEST, new FieldError(paramName, deserializerException.getMessage()));
        }
    }
}

