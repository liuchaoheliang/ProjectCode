namespace java com.froad.thrift.monitor.vo

struct BizMethodInfo{
   /* method name*/
   1: string name;
   /* args num*/
   2: byte argsNum;
   /* args type*/
   3: list<string> argsType;
}

struct BizMethodInvokeInfo{
   /*Call the total number*/
   1: i64 totalCount;
   /*Call the success number*/
   2: i64 successCount;
   /*Call abnormal failure number*/
   3: i64 failureCount;
   /*The call is successful, on average, millisecond time units*/
   4: i64 successAverageTime;
   /*The call is successful, on minimum, millisecond time units*/
   5: i64 successMinTime;
   /*The call is successful, on maximum , millisecond time units*/
   6: i64 successMaxTime;
}
