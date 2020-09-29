package observable.other;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class TestSingle {
    public static void main(String[] args) {
        Single.create(new SingleOnSubscribe<Integer>() {
            public void subscribe(@NonNull SingleEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(1);
            }
        }).subscribe(new SingleObserver<Integer>() {
            public void onSubscribe(@NonNull Disposable d) {

            }

            public void onSuccess(@NonNull Integer integer) {

            }

            public void onError(@NonNull Throwable e) {

            }
        });
    }
}
