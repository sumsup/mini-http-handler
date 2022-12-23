package org.example.counter;

public class Counter implements Runnable {
    private int count = 0; // 싱글톤 객체에서 상태를 갖게 하면(stateful). 멀티 쓰레드 환경에서 예상하지 못한 상태값 변경이 일어남.

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public int getValue() {
        return count;
    }

    @Override
    public void run() {
        // Race Condition 일 때. 동기화 처리를 해서 상태값 변경을 예상한대로 작동 하도록 구현.
        // synchronized 처리를 하지 않고 main 로직을 수행하면. count 값이 중구난방으로 변한다.
        synchronized (this) {
            this.increment();
            System.out.println("Value for Thread After increment "
                    + Thread.currentThread().getName() + " " + this.getValue()); // 1.

            this.decrement();
            System.out.println("Value for Thread at last "
                    + Thread.currentThread().getName() + " " + this.getValue()); // 0.
        }
    }

}
