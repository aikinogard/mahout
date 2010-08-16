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

import org.apache.mahout.math.Vector;

/**
 * An encoder that does the standard thing for a virtual bias term.
 */
public class ConstantValueEncoder extends FeatureVectorEncoder {
  public ConstantValueEncoder(String name) {
    super(name);
  }

  @Override
  public void addToVector(String originalForm, double weight, Vector data) {
    for (int i = 0; i < probes; i++) {
      int n = hash(name, i, data.size());
      trace(name, null, n);
      data.set(n, data.get(n) + weight);
    }
  }

  @Override
  public String asString(String originalForm) {
    return name;
  }
}
