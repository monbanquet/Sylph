package fr.monbanquet.sylph;

import fr.monbanquet.sylph.delegate.HttpResponseDelegate;

import java.net.http.HttpResponse;
import java.util.List;

public abstract class SylphHttpResponse<T, U> extends HttpResponseDelegate<T> {

    public SylphHttpResponse(HttpResponse<T> response) {
        super(response);
    }

    abstract U asObject();

    abstract List<U> asList();
}
