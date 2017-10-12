package advance.rocketmq;

import java.util.List;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import advance.store.Message;
import advance.util.SqlUtil;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Consumer，订阅消息
 */
public class Consumer {
    private  Queue<Message> sqls = new ArrayBlockingQueue<Message>(100000);
    private  AtomicInteger tollExecutorCount = new AtomicInteger(0);
    private  AtomicInteger tollMesCount = new AtomicInteger(0);
    private static Consumer consumer = null;
    private Consumer(){}
    public static  Consumer getInstance(){
        if(consumer == null){
            synchronized (Consumer.class){
                if(consumer == null){
                    consumer = new Consumer();
                }
            }
        }
        return consumer;
    }

    public static void main(String[] args) {
        Consumer.getInstance().start();
    }

    public   synchronized void saveMsg(List<MessageExt> msgs){
        if(msgs != null){
            Message message = null;
            for (MessageExt me:msgs) {
                message = new Message();
                message.setMsg(new String(me.getBody()));
                message.setMsgid(me.getMsgId());
                message.setQueueid(me.getQueueId());
                sqls.add(message) ;
                tollExecutorCount.incrementAndGet();
            }
        }
    }

   public   void execSqls(){
       while(true){
           if (!sqls.isEmpty()){
               SqlUtil.getSqlSession().insert("advance.store.MessageMapper.insert",sqls.poll());
           }else{
               System.out.println("========================正在等待数据 队列总数【"+tollExecutorCount+"】 收到消息总数【"+tollMesCount+"】==========================");
               try {
                   Thread.sleep(1000L);
               } catch (InterruptedException e) { }
           }

       }
   }

   public void start(){
       try {

           DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_m_n");
           consumer.setNamesrvAddr("172.17.6.14:9876;172.17.6.15:9876");//master集群地址
           consumer.setInstanceName("consumer");
           consumer.setConsumeMessageBatchMaxSize(100);
           consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
           consumer.subscribe("topic_m_n", "*");
           consumer.registerMessageListener(new MessageListenerConcurrently() {
               public  ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                   try {
                       saveMsg(msgs);
                       tollMesCount.addAndGet(msgs.size());
                   } catch (Exception e) {
                       return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                   }
                   return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
               }
           });
           consumer.start();
           startTimer();
           System.out.println("Consumer Started.");
           execSqls();


       }catch (Exception e){}

   }


   public static  void startTimer(){
       java.util.Timer timer = new java.util.Timer();
       timer.schedule(new TimerTask() {
           @Override
           public void run() {
               SqlUtil.commit();
           }
       },1000,10000);
   }


}
