library(e1071)
library(printr)
dat<-read.csv("C:/Users/Desktop/corpus/train.csv")
dat<-read.csv("C:/Users/Desktop/corpus/euadr/euadr.csv")
train.indeces <- sample(1:nrow(dat), 200)
dat.train <- dat[train.indeces, ]

dat.test <- dat[-train.indeces, ]
model <- svm(ASSOCIATION_TYPE ~ ., data = dat.train)
results <- predict(object = model, newdata = dat.test, type = "ASSOCIATION_TYPE")
results
table(results, dat.test$ASSOCIATION_TYPE)
summary(results)

dat<-read.csv("C:/Users/ssent/Desktop/corpus/train.csv")
test.indeces <- sample(1:nrow(dat), 2100)