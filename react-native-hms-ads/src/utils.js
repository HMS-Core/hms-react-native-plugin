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

// /////////////////////////////////////////////////////////
// JS UTILITIES
// /////////////////////////////////////////////////////////

const isFunction = (obj) => !!(obj && obj.constructor && obj.call && obj.apply);
const isString = (it) => typeof it === 'string' || it instanceof String;
const isBoolean = (val) => typeof val === 'boolean';
const isObject = (obj) => typeof obj === 'object';
const isNumeric = (num) => !isNaN(num);
const isInt = (num) => Number.isInteger(num);
const isArray = (arr) => Array.isArray(arr);

// /////////////////////////////////////////////////////////
// GENERAL UTILS
// /////////////////////////////////////////////////////////

const ArrayType = function (elemType) {
  this.elemType = elemType;
};

const arrayOf = (elemType) => new ArrayType(elemType);

const isArrayType = (x) => x.constructor === ArrayType;

const checkerList = {
  string: isString,
  function: isFunction,
  boolean: isBoolean,
  number: isNumeric,
  float: isNumeric,
  double: isNumeric,
  integer: isInt,
  array: isArray,
  object: isObject,
};

const isTypeOf = (arg, type) => {
  const checker = checkerList[type];
  return checker ? checker(arg) : false;
};

export const typeCheck = (args, checker) => {
  if (isString(checker)) {
    if (!isTypeOf(args, checker))
      throw new TypeError(
        `Arg type mismatch. Required ${checker}, found ${JSON.stringify(
          args,
        )}.`,
      );
  } else if (isArrayType(checker)) {
    if (!isArray(args))
      throw new TypeError(
        `Expected an array of ${JSON.stringify(
          checker.elemType,
        )} but got ${JSON.stringify(args)}.`,
      );

    args.forEach((arg) => {
      typeCheck(arg, checker.elemType);
    });
  } else if (isArray(checker)) {
    if (args.length !== checker.length)
      throw new TypeError(
        `Arg length mismatch. Required ${checker.length}, found ${
          args.length
        } args in ${JSON.stringify(args)}.`,
      );

    checker.forEach((typ, idx) => {
      typeCheck(args[idx], typ);
    });
  } else {
    Object.keys(checker).forEach((fieldName) => {
      const isRequired = fieldName.endsWith('!');
      const realFieldName = isRequired ? fieldName.slice(0, -1) : fieldName;

      if (args.hasOwnProperty(realFieldName)) {
        typeCheck(args[realFieldName], checker[fieldName]);
      } else if (isRequired) {
        throw new TypeError(
          `Missing field '${realFieldName}' in given args: ${JSON.stringify(
            args,
          )}.`,
        );
      }
    });
  }
};

export const addCheckerToFunction = function (fn, checker) {
  return function () {
    typeCheck(arguments, checker);
    return fn.apply(this, arguments);
  };
};

export const addCheckerToModule = function (module, checkedFunctions) {
  Object.keys(checkedFunctions).map((c) => {
    module[c] =
      module[c] && addCheckerToFunction(module[c], checkedFunctions[c]);
  });
};

export const addListenerToModule = function (module, emitter, events) {
  const subscriptions = new Map();

  events.map((event) => {
    module[event + 'ListenerAdd'] = (handler) =>
      subscriptions.set(event, emitter.addListener(event, handler));
    module[event + 'ListenerRemove'] = () => subscriptions.get(event).remove();
  });

  module.allListenersRemove = () => subscriptions.forEach((a) => a.remove());
};
