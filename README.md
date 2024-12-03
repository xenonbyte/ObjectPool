# ObjectPool

用于管理和重用对象实例的对象池，适用于对象频繁创建和销毁的场景

## Use

### 创建对象复用池
```kotlin
val pool = object : ObjectPool<Person>() {
    //对象复用时对象初始化
    override fun reuse(instance: Person, vararg args: Any?) {
        instance.name = args[0] as String
        instance.age = args[0] as Int
    }

    //对象创建时对象初始化
    override fun create(vararg args: Any?): Person {
        val name = args[0] as String
        val age = args[1] as Int
        return Person(name, age)
    }

}
```
### 获取对象
从对象复用池获取对象，当对象复用池里存在可用对象取出复用，不存在则创建
```kotlin
val person = pool.obtain("andy", 4)
```
### 回收对象
把使用完的对象放入对象复用池，方便复用
```kotlin
pool.recycle(person)
```
### 实现Reusable接口
使用对象复用池管理的对象类必须实现`Reusable`

```kotlin
//实体类例子
class Person(val name: String, val age: Int) : Reusable
```


## Download
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
  implementation 'com.longcin:objectpool:1.0.0'
}
```

## License
Copyright 2024 xubo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

