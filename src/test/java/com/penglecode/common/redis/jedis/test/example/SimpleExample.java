package com.penglecode.common.redis.jedis.test.example;

import com.penglecode.common.redis.jedis.ms.MasterSlaveJedis;
import com.penglecode.common.redis.jedis.ms.MasterSlaveJedisSentinelPool;
import com.penglecode.common.redis.jedis.test.AbstractJedisExample;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.Pool;

import java.util.*;

public class SimpleExample {

	@Test
	public void sentinelMasterSlaveJedis() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(300);
		config.setMinIdle(20);
		config.setMaxTotal(500);
		config.setTestOnBorrow(true);

		Set<String> sentinels = new LinkedHashSet<String>();
		sentinels.add("10.0.21.52:6383");
		sentinels.add("10.0.21.61:6381");
		Pool<MasterSlaveJedis> masterSlaveJedisPool = new MasterSlaveJedisSentinelPool("sentinel-10.0.21.52-6382", sentinels, config, 2000, null, 15);

		MasterSlaveJedis masterSlaveJedis = masterSlaveJedisPool.getResource();
//>>> masterSlaveJedis = MasterSlaveJedis {master=192.168.137.101:6379, slaves=[192.168.137.101:6380, 192.168.137.101:6381]}
		System.out.println(">>> masterSlaveJedis = " + masterSlaveJedis);

		masterSlaveJedis.select(15);
		masterSlaveJedis.set("nowTime", String.valueOf(System.currentTimeMillis())); // The underlying actually call the master.set("nowTime", "2015-03-16 15:34:55");
	}
}
