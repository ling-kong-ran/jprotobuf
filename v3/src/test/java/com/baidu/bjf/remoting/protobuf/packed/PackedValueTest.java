/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baidu.bjf.remoting.protobuf.packed;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.baidu.bjf.remoting.protobuf.packed.PackedProtos.Person;
import com.baidu.bjf.remoting.protobuf.packed.PackedProtos.Person.Builder;
import com.google.protobuf.ByteString;

/**
 * The Class PackedValueTest.
 *
 * @author xiemalin
 * @since 2.0.5
 */
public class PackedValueTest {

    /**
     * Test float value encode.
     */
    @Test
    public void testFloatValueEncode() {

        Builder b1 = Person.newBuilder();
        com.baidu.bjf.remoting.protobuf.packed.PackedProtosV2.Person.Builder b2 =
                com.baidu.bjf.remoting.protobuf.packed.PackedProtosV2.Person.newBuilder();

        PackedProtosPOJO pojo = new PackedProtosPOJO();
        PackedProtosPOJO2 pojo2 = new PackedProtosPOJO2();
        for (int i = 0; i < 10; i++) {
            b1.addId(i);
            b1.addName("name" + i);
            b1.addBoolF(i % 2 == 0);
            b1.addBytesF(ByteString.copyFrom(new byte[] { 'a', 'b', 'c' }));
            b1.addDoubleF(101.1d * i);
            b1.addEmail("xiemalin" + i + "@baidu.com");
            b1.addFloatF(102.1f * i);
            
            b2.addId(i);
            b2.addName("name" + i);
            b2.addBoolF(i % 2 == 0);
            b2.addBytesF(ByteString.copyFrom(new byte[] { 'a', 'b', 'c' }));
            b2.addDoubleF(101.1d * i);
            b2.addEmail("xiemalin" + i + "@baidu.com");
            b2.addFloatF(102.1f * i);

            pojo.getId().add(i);
            pojo.getName().add("name" + i);
            pojo.getBoolF().add(i % 2 == 0);
            pojo.getBytesF().add(new byte[] { 'a', 'b', 'c' });
            pojo.getDoubleF().add(101.1d * i);
            pojo.getEmail().add("xiemalin" + i + "@baidu.com");
            pojo.getFloatF().add(102.1f * i);
            
            pojo2.getId().add(i);
            pojo2.getName().add("name" + i);
            pojo2.getBoolF().add(i % 2 == 0);
            pojo2.getBytesF().add(new byte[] { 'a', 'b', 'c' });
            pojo2.getDoubleF().add(101.1d * i);
            pojo2.getEmail().add("xiemalin" + i + "@baidu.com");
            pojo2.getFloatF().add(102.1f * i);
        }
        Person person = b1.build();
        com.baidu.bjf.remoting.protobuf.packed.PackedProtosV2.Person person2;
        person2 = b2.build();
        Codec<PackedProtosPOJO> codec = ProtobufProxy.create(PackedProtosPOJO.class);
        Codec<PackedProtosPOJO2> codec2 = ProtobufProxy.create(PackedProtosPOJO2.class);
        
        try {
            byte[] bytes = codec.encode(pojo); // packed bytes
            Assert.assertArrayEquals(person.toByteArray(), bytes);

            byte[] bytes2 = codec2.encode(pojo2);
            Assert.assertArrayEquals(person2.toByteArray(), bytes2);

            PackedProtosPOJO pojoNew = codec.decode(bytes);
            Assert.assertEquals(pojo.getId(), pojoNew.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
