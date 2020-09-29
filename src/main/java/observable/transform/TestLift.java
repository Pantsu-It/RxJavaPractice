package observable.transform;

import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import main.ObserverUtils;

public class TestLift {
    public static void main(String[] args) {
        Observable.just(1, 2, 3)
                .lift(new ObservableOperator<String, Integer>() {
                    @NonNull
                    public Observer<? super Integer> apply(@NonNull final Observer<? super String> observer) throws Exception {
                        return new Observer<Integer>() {
                            public void onSubscribe(@NonNull Disposable d) {
                                observer.onSubscribe(d);
                            }

                            public void onNext(@NonNull Integer integer) {
                                observer.onNext(String.valueOf((char) ('a' + integer)));
                            }

                            public void onError(@NonNull Throwable e) {
                                observer.onError(e);
                            }

                            public void onComplete() {
                                observer.onComplete();
                            }
                        };
                    }
                })
                .subscribe(ObserverUtils.stringObserver());
    }
}
