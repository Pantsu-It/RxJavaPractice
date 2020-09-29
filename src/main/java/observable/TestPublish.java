package observable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class TestPublish {
  public static void main(String[] args) {
    Consumer<Long> subscriber1 = aLong -> System.out.println("subscriber1: " + aLong);
    Consumer<Long> subscriber2 = aLong -> System.out.println("   subscriber2: " + aLong);
    Consumer<Long> subscriber3 = aLong -> System.out.println("      subscriber3: " + aLong);

    ConnectableObservable<Long> connectableObservable =
        Observable.create((ObservableOnSubscribe<Long>) e ->
            Observable
                .interval(10, TimeUnit.MILLISECONDS, Schedulers.computation())
                .take(Integer.MAX_VALUE)
                .subscribe(e::onNext))
            .observeOn(Schedulers.newThread())
            .publish();
    connectableObservable.connect();

    Observable<Long> observable = connectableObservable.refCount();

    Disposable disposable1 = observable.subscribe(subscriber1);
    Disposable disposable2 = observable.subscribe(subscriber2);
    observable.subscribe(subscriber3);

    try {
      Thread.sleep(20L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    disposable1.dispose();
    disposable2.dispose();

    System.out.println("subscriber1、subscriber2 重新订阅");

    try {
      Thread.sleep(20L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    disposable1 = observable.subscribe(subscriber1);
    disposable2 = observable.subscribe(subscriber2);


    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
