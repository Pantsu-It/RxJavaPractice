package observable;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class TestTransform2 {
  public static void main(String[] args) throws InterruptedException {
    // T -> GroupedObservable<ValueSelector> (getKey=KeySelector)
//    groupBy();

    // T -> List<T>
//    bufferByCount();
//    bufferByTimeSpan();

    // T -> Observable<T>
    windowByCount();
//    windowByTimeSpan();

    // Tn -> T1*T2*...*Tn
//    scan();
//    scanWith();

    // 异步操作需要阻塞main方法
    Thread.sleep(100000);
  }

  private static void groupBy() {
    Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .take(10)
        .doOnNext(longValue -> {
          System.out.println("emit item:" + longValue);
        })
        .groupBy(longValue -> longValue % 3)
        .doOnNext(groupedObservable -> {
          System.out.println("# emit group Key=" + groupedObservable.getKey());
        })
        .subscribe(groupedObservable ->
            groupedObservable.subscribe(longValue2 -> {
              System.out.println(
                  "## emit item Key=" + groupedObservable.getKey() + " value=" + longValue2);
            })
        );
  }


  private static void bufferByCount() {
    Observable
        .interval(200, TimeUnit.MILLISECONDS)
        .buffer(3)
        .subscribe(longList -> System.out.println("longList:" + longList.toString()));
  }

  /**
   * buffer区间内，如果没有数据发送，buffer数据流会发出一个空列表
   */
  private static void bufferByTimeSpan() {
    Observable
        .interval(200, TimeUnit.MILLISECONDS)
        .buffer(1000, TimeUnit.MILLISECONDS)
        .subscribe(longList -> System.out.println("longList:" + longList.toString()));
  }

  private static void windowByCount() {
    Observable
        .interval(200, TimeUnit.MILLISECONDS)
        .window(3)
        .subscribe(longObservable -> System.out.println("observable:" + longObservable.toString()));
  }

  private static void windowByTimeSpan() {
    Observable
        .interval(200, TimeUnit.MILLISECONDS)
        .window(1000, TimeUnit.MILLISECONDS)
        .subscribe(longObservable -> System.out.println("observable:" + longObservable.toString()));
  }

  private static void scan() {
    // 输出结果：
    // a
    // a+b
    // a+b+c
    Observable
        .just("a", "b", "c")
        .scan((str1, str2) -> str1 + "+" + str2)
        .subscribe(System.out::println);
  }

  private static void scanWith() {
    // 输出结果：
    // x
    // x+a
    // x+a+b
    // x+a+b+c
    Observable
        .just("a", "b", "c")
        .scanWith(() -> "x", (str1, str2) -> str1 + "+" + str2)
        .subscribe(System.out::println);
  }
}
