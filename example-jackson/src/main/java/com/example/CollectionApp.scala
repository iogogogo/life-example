package com.example

import java.util

import org.slf4j.LoggerFactory

object CollectionApp {


  def main(args: Array[String]): Unit = {
    val log = LoggerFactory.getLogger(getClass)

    import scala.collection.JavaConverters._

    val models: util.List[Model] = new util.ArrayList[Model]()

    models.add(Model("vm_11_11", "192.168.1.0", 1))
    models.add(Model("vm_11_11", "192.168.1.1", 2))
    models.add(Model("vm_11_11", "192.168.1.2", 3))
    models.add(Model("vm_11_11", "192.168.1.3", 4))
    models.add(Model("vm_11_11", "192.168.1.2", 5))
    models.add(Model("vm_11_12", "192.168.1.3", 6))
    models.add(Model("vm_11_12", "192.168.1.6", 7))
    models.add(Model("vm_11_12", "192.168.1.7", 8))
    models.add(Model("vm_11_12", "192.168.1.8", 9))
    models.add(Model("vm_11_12", "192.168.1.9", 10))
    models.add(Model("vm_11_13", "192.168.1.10", 11))
    models.add(Model("vm_11_13", "192.168.1.11", 12))
    models.add(Model("vm_11_13", "192.168.1.11", 13))
    models.add(Model("vm_11_13", "192.168.1.12", 14))
    models.add(Model("vm_11_13", "192.168.1.11", 15))
    models.add(Model("vm_11_13", "192.168.1.15", 16))
    models.add(Model("vm_11_16", "192.168.1.16", 17))
    models.add(Model("vm_11_16", "192.168.1.17", 18))
    models.add(Model("vm_11_16", "192.168.1.18", 19))
    models.add(Model("vm_11_16", "192.168.1.19", 20))
    models.add(Model("vm_11_16", "192.168.1.20", 21))
    models.add(Model("vm_11_16", "192.168.1.21", 22))
    models.add(Model("vm_11_16", "192.168.1.22", 23))

    val list = models.asScala

    val res = list.groupBy(x => x.hostname + x.ip).map {
      case (k, v) =>
        (k, v.map(_.order).min)
    }

    log.info(s"$res")
  }
}

case class Model(hostname: String, ip: String, order: Int)