/*
Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

export const isArray = val => {
  return Array.isArray(val);
};

export const isNumber = val => {
  return Number(val) === val;
};

export const isFloat = val => {
  return isNumber(val) && val % 1 !== 0;
};

export const isString = val => {
  return typeof val === 'string';
};

export const isBoolean = val => {
  return typeof val === 'boolean';
};

export const areKeysExact = (objOne, objTwo) => {
  if (objTwo.constructor !== {}.constructor) {
    return false;
  }

  let isExact = true;
  const objTwoKeys = Object.keys(objTwo);
  Object.keys(objOne).forEach(val => {
    if (!objTwoKeys.includes(val)) {
      isExact = false;
    }
  });
  return isExact;
};
