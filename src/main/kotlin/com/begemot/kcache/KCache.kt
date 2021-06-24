package com.begemot.kcache

import com.begemot.knewscommon.Found
import com.begemot.knewscommon.Found2
import com.begemot.knewscommon.StoredElement
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.StorageOptions
import com.google.cloud.storage.Blob
import mu.KotlinLogging
import kotlin.system.measureTimeMillis

val loggerk = KotlinLogging.logger {}

//val KF=hashMapOf<String,String>()

class KCache(){

   val bucketName="knews1939.appspot.com"
   val st by lazy {  StorageOptions.getDefaultInstance().service }
   val bucket by lazy { st.get(bucketName) }


   fun dateParent(sNameFile:String,lacronim: String):Long {
        val c=bucket.get(sNameFile.substring(0,sNameFile.length-lacronim.length)).blobId
        return st.get(c).updateTime
   }
   fun findInCache2(sNameFile:String,lacronim:String): Found {
      loggerk.debug { "Hi I'm findInCache2" }
      val c=bucket.get(sNameFile)
      loggerk.debug{ "$sNameFile  " }
      if(c!=null)  return Found(true,dateParent(sNameFile,lacronim),String(st.get(c.blobId).getContent()))
      return Found(false,0,"$sNameFile not found")
   }

   fun findInCache(sNameFile:String): Found {
      val c=bucket.get(sNameFile)
      if(c!=null) return Found(true,st.get(c.blobId).updateTime,String(st.get(c.blobId).getContent()))
      return Found(false,0,"$sNameFile not found")
   }
   fun findInCacheImg(sNameFile:String): Found2 {
      val c=bucket.get(sNameFile)
      if(c!=null) return Found2(true,st.get(c.blobId).getContent())
      return Found2(false,ByteArray(0))
   }
   fun storeInCache(sNameFile:String,sContent:String):Long{
       val i=  bucket.create(sNameFile,sContent.toByteArray())
       if(i==null) loggerk.error{" NULL in create bucked  $sNameFile"}
       else loggerk.debug { "bucked created OK $sNameFile" }
       return i.updateTime
   }

   fun listFiles():List<StoredElement>{
      val lSE= mutableListOf<StoredElement>()
      val bl=bucket.list()
      for(b in bl.values){
         //b.etag+b.updateTime
         lSE.add(StoredElement(b.name,b.etag,b.createTime,b.updateTime,b.size))

        //? b.deleteTime
      }
      return lSE
   }

   fun deleteFiles(){
      println("cache Delete files called")
      val bl=bucket.list()
      val ldirectories= listOf("Images/","Books/","Articles/","HeadLines/")
      for(b in bl.values){


         if(b.name?.contains("Images") != true && b.name?.contains("Books") != true) {
            if(b.name !in ldirectories) {
               loggerk.debug { "removing ${b.name}" }
               println("removing ${b.name}")
               st.delete(b.blobId)
            }
         }
         if(b.name?.contains("Books/Bulgakov")==true){
            if(b.name?.takeLast(1)?.get(0)?.isDigit()==false){
               println("removing ${b.name}")
               st.delete(b.blobId)
            }
         }


      }
      //setup()

   }
   fun deleteHeadlines(handler:String){
      val fName="HeadLines/${handler}"
      val bl=bucket.list()
      for(b in bl.values){
         if(b.name?.contains(fName) == true)
            if(b.name?.equals(fName)==false)
            st.delete(b.blobId)
      }
      //setup()
   }


}