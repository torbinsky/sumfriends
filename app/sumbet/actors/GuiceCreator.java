package sumbet.actors;

import akka.japi.Creator;

import com.google.inject.Injector;

public class GuiceCreator<T> implements Creator<T> {
    private final Class<T> clazz;
    private final Injector injector;

    public GuiceCreator(Injector injector, Class<T> clazz) {
        this.injector = injector;
        this.clazz = clazz;
    }

    @Override
    public T create() throws Exception {
        return injector.getInstance(clazz);
    }
}
