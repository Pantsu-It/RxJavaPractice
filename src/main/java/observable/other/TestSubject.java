package observable.other;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class TestSubject {

  public static void main(String[] args) {
//    testPublishSubject();
    testBehaviorSubject();
  }

  private static void testPublishSubject() {
    Subject<Integer> subject = PublishSubject.create();
//    subject.onComplete();
    subject.onError(new Exception("exception"));
    subject.subscribe(
        value -> System.out.println("onNext:" + value),
        throwable -> System.out.println("onError:" + throwable.getMessage()),
        () -> System.out.println("onComplete")
    );
    subject.onNext(123);
    subject.onNext(123);
//    subject.onComplete();
//    subject.onError(new Exception("exception"));
  }
  private static void testBehaviorSubject() {
    Subject<Integer> subject = BehaviorSubject.createDefault(0);
    subject.onNext(123);
//    subject.onComplete();
    subject.onError(new Exception("exception"));
    subject.subscribe(
        value -> System.out.println("onNext:" + value),
        throwable -> System.out.println("onError:" + throwable.getMessage()),
        () -> System.out.println("onComplete")
    );
    subject.onNext(123);
//    subject.onComplete();
//    subject.onError(new Exception("exception"));
  }
}
