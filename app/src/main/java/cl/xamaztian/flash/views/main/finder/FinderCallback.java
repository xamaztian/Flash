package cl.xamaztian.flash.views.main.finder;

public interface FinderCallback {

    void error(String error);
    void success();
    void notFound();
}
