package com.begemot.kcache

import com.begemot.knewscommon.Found
import com.begemot.knewscommon.Found2
import com.begemot.knewscommon.StoredElement
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.StorageOptions
import com.google.cloud.storage.Blob
import kotlin.system.measureTimeMillis


//val KF=hashMapOf<String,String>()

class KCache(){
   val bucketName="knews1939.appspot.com"
   val st by lazy { setup(); StorageOptions.getDefaultInstance().service }
   val bucket by lazy { st.get(bucketName) }
   companion object KFiles {
      val kfiles =hashMapOf<String,BlobId>()
      fun setup(){
         kfiles.clear()
         val st=StorageOptions.getDefaultInstance().service
         val bucket=st.get("knews1939.appspot.com")

         val bl=bucket.list()
         for(b in bl.values){
            kfiles[b.name]=b.blobId
           // println("${b.name}")
         }
      }
   }

   fun dateParent(sNameFile:String,lacronim: String):Long {
        //val c1 = kfiles[sNameFile.substring(0,sNameFile.length-lacronim.length)]
        val c=bucket.get(sNameFile.substring(0,sNameFile.length-lacronim.length)).blobId
        return st.get(c).updateTime
   }
   fun findInCache2(sNameFile:String,lacronim:String): Found {
      val start = System.currentTimeMillis()
      val c=bucket.get(sNameFile)
      println("$sNameFile  ")

      if(c!=null)  return Found(true,dateParent(sNameFile,lacronim),String(st.get(c.blobId).getContent()))
      return Found(false,0,"$sNameFile not found")

   }
   fun findInCache(sNameFile:String): Found {
      //val c2 = BlobId.of(bucketName, sNameFile)  //¿?????
      val s=bucket   //to ensure the setUp!!!!
      val c= kfiles[sNameFile]

      //if(c!=null) return Found(true,dateParent(sNameFile),String(st.get(c).getContent()))
      if(c!=null) return Found(true,st.get(c).updateTime,String(st.get(c).getContent()))
      return Found(false,0,"$sNameFile not found")
   }
   fun findInCacheImg(sNameFile:String): Found2 {
      //val c = BlobId.of(bucketName, sNameFile)  //¿?????
      val s=bucket //to ensure the set up
      val c= kfiles[sNameFile]
      if(c!=null) return Found2(true,st.get(c).getContent())
      return Found2(false,ByteArray(0))
   }
   fun storeInCache(sNameFile:String,sContent:String):Long{
       val i=  bucket.create(sNameFile,sContent.toByteArray())

      //i.update()
       if(i==null) println(" NULL in create bucked  $sNameFile")
       else println("create bucked NOT null $sNameFile")
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
      for(b in bl.values){
         if(b.name?.contains("images") != true)
         st.delete(b.blobId)
      }
      setup()

   }
   fun deleteHeadlines(handler:String){
      val fName="HeadLines/${handler}"
      val bl=bucket.list()
      for(b in bl.values){
         if(b.name?.contains(fName) == true)
            if(b.name?.equals(fName)==false)
            st.delete(b.blobId)
      }
      setup()
   }


}