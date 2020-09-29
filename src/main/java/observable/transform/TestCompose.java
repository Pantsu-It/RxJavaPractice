package observable.transform;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TestCompose {
    public static void main(String[] args) {
        final ObservableOperator<Character, Integer> lift1 = new ObservableOperator<Character, Integer>() {
            @NonNull
            public Observer<? super Integer> apply(@NonNull final Observer<? super Character> observer) throws Exception {
                return new Observer<Integer>() {
                    public void onSubscribe(@NonNull Disposable d) {
                        observer.onSubscribe(d);
                    }

                    public void onNext(@NonNull Integer integer) {
                        observer.onNext(Character.valueOf((char) ('a' + integer)));
                    }

                    public void onError(@NonNull Throwable e) {
                        observer.onError(e);
                    }

                    public void onComplete() {
                        observer.onComplete();
                    }
                };
            }
        };
        final ObservableOperator<String, Character> lift2 = new ObservableOperator<String, Character>() {
            @NonNull
            public Observer<? super Character> apply(@NonNull final Observer<? super String> observer) throws Exception {
                return new Observer<Character>() {
                    public void onSubscribe(@NonNull Disposable d) {
                        observer.onSubscribe(d);
                    }

                    public void onNext(@NonNull Character character) {
                        observer.onNext(String.valueOf(character));
                    }

                    public void onError(@NonNull Throwable e) {
                        observer.onError(e);
                    }

                    public void onComplete() {
                        observer.onComplete();
                    }
                };
            }
        };

        Observable.just(1, 2, 3)
                .compose(new ObservableTransformer<Integer, String>() {
                    @NonNull
                    public ObservableSource<String> apply(@NonNull Observable<Integer> upstream) {
                        return upstream.lift(lift1).lift(lift2);
                    }
                });
    }
}
