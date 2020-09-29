package observable.other;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TestMaybe {
    public static void main(String[] args) {
        create().subscribe(new MaybeObserver() {
            public void onSubscribe(@NonNull Disposable d) {

            }

            public void onSuccess(@NonNull Object o) {

            }

            public void onError(@NonNull Throwable e) {

            }

            public void onComplete() {

            }
        });
    }

    public static Maybe create() {
        return Maybe.create(new MaybeOnSubscribe<Integer>() {
            public void subscribe(@NonNull MaybeEmitter<Integer> emitter) throws Exception {
                try {
                    Thread.sleep(1000);
                    emitter.onSuccess(1000);
                } catch (InterruptedException e) {
                    emitter.onError(e);
                }
                emitter.onComplete();
            }
        });
    }
}
