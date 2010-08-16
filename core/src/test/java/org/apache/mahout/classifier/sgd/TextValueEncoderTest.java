/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.classifier.sgd;

import com.google.common.collect.ImmutableMap;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextValueEncoderTest {
  @Test
  public void testAddToVector() {
    TextValueEncoder enc = new TextValueEncoder("text");
    Vector v1 = new DenseVector(200);
    enc.addToVector("test1 and more", v1);
    // should set 6 distinct locations to 1
    assertEquals(6.0, v1.norm(1), 0);
    assertEquals(1.0, v1.maxValue(), 0);

    // now some fancy weighting
    StaticWordValueEncoder w = new StaticWordValueEncoder("text");
    w.setDictionary(ImmutableMap.<String, Double>of("word1", 3.0, "word2", 1.5));
    enc.setWordEncoder(w);

    // should set 6 locations to something
    Vector v2 = new DenseVector(200);
    enc.addToVector("test1 and more", v2);

    // this should set the same 6 locations to the same values
    Vector v3 = new DenseVector(200);
    w.addToVector("test1", v3);
    w.addToVector("and", v3);
    w.addToVector("more", v3);

    assertEquals(0, v3.minus(v2).norm(1), 0);

    // moreover, the locations set in the unweighted case should be the same as in the weighted case
    assertEquals(v3.zSum(), v3.dot(v1), 0);
  }

  @Test
  public void testAsString() {
    TextValueEncoder enc = new TextValueEncoder("text");
    assertEquals("[text:test1:1.0000, text:and:1.0000, text:more:1.0000]", enc.asString("test1 and more"));
  }
}
