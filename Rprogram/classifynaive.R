
 dat<-read.csv("C:/Users/ssent/Desktop/redata/text.csv")
 library(e1071)
 library(printr)
 train.indeces <- sample(1:nrow(dat), 200)
 dat.train <- dat[train.indeces, ]
 dat.test <- dat[-train.indeces, ]
 model <- naiveBayes(x = subset(dat.train, select=-ASSOCIATION_TYPE), y = dat.train$ASSOCIATION_TYPE)
 results <- predict(object = model, newdata = dat.test)
table(results, dat.test$ASSOCIATION_TYPE)
summary(results)

svm(formula = class ~ ., data = dat, cost = 100, gamma = 1)