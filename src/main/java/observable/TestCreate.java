package observable;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

public class TestCreate {
  public static void main(String[] args) {
    Observable.just(0);
    Observable.just(1, 2, 3);

    Observable.fromArray(1, 2, 3);
    Observable.fromIterable(new ArrayList<Integer>());

    Observable.fromCallable(() -> 1 + 2 + 3);
    Observable.fromFuture(new FutureTask<>(() -> {
      Thread.sleep(1000);
      return 1 + 2 + 3;
    }));

    Observable.fromPublisher((Publisher<Integer>) s -> {
      int result = 0;
      // do something...
      if (result > 0) {
        s.onNext(result);
        s.onComplete();
      } else {
        s.onError(new Exception("result = 0"));
      }
    });

    Observable.create(null);
    Observable.defer(() -> Observable.just(System.currentTimeMillis()));
    Observable.range(0, 10);
    Observable.rangeLong(0, 100000000000L);


    Observable.timer(1, TimeUnit.SECONDS);
    Observable.interval(1, 5, TimeUnit.SECONDS);
    Observable.intervalRange(10000, 20000, 1, 5, TimeUnit.SECONDS);


    Observable.empty();
    Observable.error(new Exception("empty message"));
    Observable.never();
  }
}
