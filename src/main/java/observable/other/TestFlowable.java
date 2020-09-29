package observable.other;

import io.reactivex.*;

import java.util.concurrent.TimeUnit;

public class TestFlowable {

    public static void main(String[] args) {
        fromObservable();
    }

    public static class InnerClass {
        public static void main(String[] args) {

        }
    }

    public static Flowable fromObservable() {
        return Observable.interval(10, TimeUnit.MILLISECONDS)
                .toFlowable(BackpressureStrategy.DROP);
    }
}
