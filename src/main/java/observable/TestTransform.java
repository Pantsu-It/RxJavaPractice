package observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class TestTransform {
  public static void main(String[] args) throws InterruptedException {
    // T -> R
//    map();

    // T -> ObservableSource<R>
    // 使用场景：1 循环嵌套 2 A的结果是B的输入
    // flatMap不能保证数据按组的顺序发射（使用merge操作observable）
//    flatMap();

    // T -> Iterable<R>
//    flatMapIterable1();
//    flatMapIterable2();

    // T -> ObservableSource<R>
    // 与flatMap相比，子数据流不会重叠，是通过
//    switchMap();

    // T -> ObservableSource<R>
    // 与flatMap相比，子数据流不会重叠，是通过concat操作连接
//    concatMap();

    // Observable<T> -> Observable<R>
    cast();

    // 异步操作需要阻塞main方法
    Thread.sleep(100000);
  }

  private static void map() {
    Observable
        .just(1, 2, 3)
        .map(intValue -> intValue + "");
  }

  private static void flatMap() {
    Observable
        .just(1, 2, 3)
        .flatMap(intValue -> Observable.range(1, intValue));
  }

  private static void flatMapIterable1() {
    Observable
        .just(new Integer[]{1}, new Integer[]{1, 2}, new Integer[]{1, 2, 3})
        .flatMapIterable(integers -> Arrays.asList(integers))
        .subscribe(System.out::println);
  }

  private static void flatMapIterable2() {
    Observable
        .just(new Integer[]{1}, new Integer[]{1, 2}, new Integer[]{1, 2, 3})
        .flatMapIterable(
            integers -> Arrays.asList(integers),
            (integers, integer) -> "in group:" + integers.toString() + " item:" + integer
        )
        .subscribe(System.out::println);
  }


  private static void switchMap() {
    // 输出结果：
    // # 0 1 # 0 1 # 0 1 # 0 1 # 0 1 ...
    Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .switchMap(intValue -> {
          System.out.print("# ");
          return Observable.interval(200, TimeUnit.MILLISECONDS).take(5);
        })
        .subscribe(integer -> System.out.print(integer + " "));
  }

  private static void concatMap() {
    // 输出结果：
    // # 0 1 2 3 4 # 0 1 2 3 4 # 0 1 ...
    Observable
        .interval(500, TimeUnit.MILLISECONDS)
        .concatMap(intValue -> {
          System.out.print("# ");
          return Observable.interval(200, TimeUnit.MILLISECONDS).take(5);
        })
        .subscribe(integer -> System.out.print(integer + " "));
  }


  private static void cast() {
    Observable
        .interval(200, TimeUnit.MILLISECONDS)
        .cast(Object.class)
        .subscribe(object -> System.out.print(object + " "));
  }

}
