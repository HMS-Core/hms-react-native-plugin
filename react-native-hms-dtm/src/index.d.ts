/*
    Copyright 2020-2022. Huawei Technologies Co., Ltd. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

declare module "@hmscore/react-native-hms-dtm" { 
    
    interface ResponseObject {

        /**
         * Succes scenario: The data is not null.
         * Failure scenario: The data is null.
         */
         data: string | boolean | number;

         /**
          * Succes scenario: The value of errorMessage is null.
          * Failure scenario: The value of errorMessage is not null.
          */
         errorMessage: string;
    }

    interface Map {

        /**
         * "Value" element of the key-value pair.
         */
         value: string | boolean | number;

         /**
          * "Key" element of a key-value pair.
          */
          key: string;
    }

    export default class HMSDtmModule {
        
        /**
         * Reports an event.
         */
        static onEvent(eventId: string, map: Map): Promise<ResponseObject>;

        /**
         * Sets the variable value in the CustomVariable class which will return to the server.
         */
        static setCustomVariable(varName: string, value: string | boolean | number): Promise<ResponseObject>;

        /**
         * Enables HMS Plugin Function Analytics.
         */
        static enableLogger(): Promise<ResponseObject>;

        /**
         * Disables HMS Plugin Function Analytics.
         */
        static disableLogger(): Promise<ResponseObject>;
    }
}