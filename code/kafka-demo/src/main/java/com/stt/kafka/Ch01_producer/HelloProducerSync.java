package com.stt.kafka.Ch01_producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

public class HelloProducerSync {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		// 创建配置对象
		Properties props = new Properties();
		// 所有的配置关键字都是在ProducerConfig中有声明
		// kafka集群配置
		props.put(BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
		// 等待所有副本节点的应答
		// 0 : 不需要应答
		// 1 : 需要leader应答
		// -1(all) : 需要所有副本应答
		props.put("acks", "all");
		// 消息发送最大尝试次数
		// 如果为0，表示失败了不向双端队列中存放
		props.put("retries", 0);
		// 一批消息处理大小
		props.put("batch.size", 16384);
		// 用于延时发送一批消息给kafka，超过该延时，即使batch没有满也要发送消息请求给kafka
		props.put("linger.ms", 1);
		// 发送缓存区内存大小
		props.put("buffer.memory", 33554432);
		// key序列化
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// value序列化
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// 表示生产者发送的消息的key和value的类型
		// key 用于分区时使用
		Producer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 100; i++){
			// 发送100个消息，key是i，value是hello-i
			// 封装数据
			ProducerRecord<String, String> record =
					new ProducerRecord<>("api_test", Integer.toString(i), "hello-" + Integer.toString(i));
			// 发送数据
			// 同步发送，发送成功之后继续发送下一个消息
			producer.send(record).get();
		}
		// 关闭资源
		producer.close();
	}
}