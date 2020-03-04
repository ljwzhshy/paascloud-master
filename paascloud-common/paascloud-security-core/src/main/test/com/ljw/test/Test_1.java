package com.ljw.test;

/**
 * @author luojiawei
 * @data 2020/3/4
 */
public class Test_1 extends   Test_0{
    @Override
    public String name() {
        return "Test_1";
    }
    public void print(){
        System.out.print(super.test());;
    }
    public  static  void  main(String[] args){
        Test_1 test_1 = new Test_1();
        test_1.print();
    }
}
