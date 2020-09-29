package observable.other;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;

public class TestCompletable {
    public static void main(String[] args) {
        create().subscribe();

        from().andThen(Observable.range(0, 1))
                .subscribe();

    }

    public static Completable create() {
        return Completable.create(new CompletableOnSubscribe() {
            public void subscribe(@NonNull CompletableEmitter emitter) {
                try {
                    Thread.sleep(1000);
                    emitter.onComplete();
                } catch (InterruptedException e) {
                    emitter.onError(e);
                }
            }
        });
    }

    public static Completable from() {
        return Completable.fromAction(new Action() {
            public void run() throws Exception {
                System.out.println("");
            }
        });
    }

}
