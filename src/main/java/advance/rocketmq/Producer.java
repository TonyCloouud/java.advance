package advance.rocketmq;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.mysql.jdbc.TimeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Producer，发送消息
 */
public class Producer {
    private static AtomicInteger counts = new AtomicInteger(0);
    private static Map<String, String> msgs = new ConcurrentHashMap<>();
    private static int sum = 0;
    private static int index = 0;


    public static void main(String[] args) throws MQClientException, InterruptedException, IOException, RemotingException, MQBrokerException {
        start();

    }

    public static void start() throws IOException, MQClientException, RemotingException, InterruptedException, MQBrokerException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DefaultMQProducer producer = new DefaultMQProducer("consumer_m_n");
        producer.setNamesrvAddr("172.17.1.134:9876");//slave集群地址
        producer.setInstanceName("producer");
        producer.setRetryTimesWhenSendFailed(10);//失败的 情况发送10次
        producer.start();
        ExecutorService es = Executors.newFixedThreadPool(10);
        while (true) {
            System.out.println("Please input push msg nums:");
            int num = Integer.valueOf(br.readLine());
            sum += num;
            System.out.println("nums【" + num + "】合计【"+sum+"】");
            while((num--)>0){
                Message msg = new Message("topic_m_n",// topic
                        "TagA",// tag
                        ("Hello RocketMQ " + index++).getBytes()// body
                );

//                es.submit(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            msgs.put(new String(msg.getBody()), new String(msg.getBody()));
//                            producer.send(msg);
//                        } catch (Exception e) {
//                        }
//                    }
//                });
                msgs.put(new String(msg.getBody()), new String(msg.getBody()));
                producer.send(msg);
            }

            br = new BufferedReader(new InputStreamReader(System.in));
        }


//        es.shutdown();
//        try {
//            es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
//        } catch (Exception ex) {
//        }
//
//        if (es.isShutdown()) {
//            System.out.println("结束信息推送，已推送信息【" + msgs.size() + "】");
//            producer.shutdown();
//        }

    }




}
