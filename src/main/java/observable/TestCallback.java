package observable;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import main.ObserverUtils;

public class TestCallback {
  public static void main(String[] args) {
    Observable.just(1, 2)
        .doOnSubscribe(TestCallback::onSubscribe)
        .doOnNext(TestCallback::onNext)
        .doAfterNext(TestCallback::afterNext)
        .doOnComplete(TestCallback::onComplete)
        .doOnTerminate(TestCallback::onTerminate)
        .doAfterTerminate(TestCallback::afterTerminate)
        .doFinally(TestCallback::onFinally)
        .subscribe(ObserverUtils.integerObserver());
  }

  private static void afterTerminate() {
    System.out.println("afterTerminate");
  }

  private static void onTerminate() {
    System.out.println("onTerminate");
  }

  private static void afterNext(Integer integer) {
    System.out.println("afterNext");
  }

  private static void onNext(Integer integer) {
    System.out.println("onNext");
  }

  private static void onFinally() {
    System.out.println("onFinally");
  }

  private static void onComplete() {
    System.out.println("onComplete");
  }

  public static void onSubscribe(Disposable disposable) {
    System.out.println("onSubscribe");
  }
}
