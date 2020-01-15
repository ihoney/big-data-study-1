package com.stt.hadoop.mr.Ch18_CommonFriends;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import static com.stt.hadoop.Constant.PATH;

public class OneShareFriendDriver {

	public static void main(String[] args) throws Exception {

		args = new String[]{PATH+"ch18/input.txt", PATH+"ch18/output"};

		Job job = Job.getInstance(new Configuration());

		job.setJarByClass(OneShareFriendDriver.class);

		job.setMapperClass(OneShareFriendMapper.class);
		job.setReducerClass(OneShareFriendReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.setInputPaths(job,new Path(args[0]));
		FileOutputFormat.setOutputPath(job,new Path(args[1]));

		boolean result = job.waitForCompletion(true);
		System.exit(result ? 0 : 1);
	}

}