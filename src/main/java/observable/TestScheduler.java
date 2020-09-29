package observable;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import main.Logger;

import static main.Logger.TAG;

public class TestScheduler {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Logger.e(TAG, "Observable subscribeOn subscribe thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        })
            .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.single())
                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.single())
                .doOnNext(new Consumer<Integer>() {
                    public void accept(@NonNull Integer integer) throws Exception {
                        Logger.e(TAG, "After observeOn(newThread)，doOnNext thread is " + Thread.currentThread().getName());
                    }
                })
//                .observeOn(Schedulers.single())
                .doOnComplete(new Action() {
                    public void run() throws Exception {
                        Logger.e(TAG, "After observeOn(newThread)，doOnComplete thread is " + Thread.currentThread().getName());
                    }
                })
//                .observeOn(Schedulers.single())
                .doOnComplete(new Action() {
                    public void run() throws Exception {
                        Logger.e(TAG, "After observeOn(newThread)，doOnComplete thread is " + Thread.currentThread().getName());
                    }
                })
//                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    public void accept(@NonNull Integer integer) throws Exception {
                        Logger.e(TAG, "After observeOn(io)，subscriber-onNext thread is " + Thread.currentThread().getName());
                    }
                }, new Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    public void run() throws Exception {
                        Logger
                            .e(TAG, "After observeOn(io)，subscriber-onComplete thread is " + Thread.currentThread().getName());
                    }
                });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
