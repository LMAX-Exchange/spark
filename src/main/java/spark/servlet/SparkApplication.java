/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spark.servlet;

import spark.Spark;

/**
 * The application entry point when Spark is run in a servlet context.
 *
 * @author Per Wendel
 */
public interface SparkApplication {

    /**
     * Invoked from the SparkFilter. Add routes here.
     *
     * @param spark the spark instance for this application
     */
    void init(Spark spark);
}
