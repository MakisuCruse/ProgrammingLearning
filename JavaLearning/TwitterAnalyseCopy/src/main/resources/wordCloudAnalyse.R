setwd("/Users/makisucruse/Downloads/OutPutFile1/");
library(wordcloud2);
ret<-read.table("JimmyKimmel.txt",comment.char = "<",sep = "~");
wordcloud2(ret);
