provider "aws" {
  region = "eu-west-1"
}

data "aws_region" "current" {}

data "aws_sns_topic" "trello-cards-sns" {
  name = "trello-cards-sns"
}
data "aws_sns_topic" "trello-cards-processor-sns" {
  name = "trello-cards-processor-sns"
}

module "trello-specific-list-lambda" {
  source                    = "../../modules/lambda"
  zip_name                  = ""
  handler                   = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest"
  function_name             = "trello-specific-list"
  runtime                   = "java8"
  memory_size               = 256

  environment {
    variables {
      key = "value"
    }
  }
}

module "cloudwatch-scheduled-event" {
  enable = "0"
  source = "../../modules/event/cloudwatch-scheduled-event"

  lambda_function_arn = "${module.trello-specific-list-lambda.arn}"
  schedule_expression = "rate(1 day)"
}

module "trello-specific-list-sns-subscription" {
  source = "../../modules/sns-subscription"
  topic_arn                 = "${data.aws_sns_topic.trello-cards-sns.arn}"
  subscription_endpoint_arn = "${module.trello-specific-list-lambda.arn}"
}

module "trello-cards-lambda" {
  source        = "../../modules/lambda"
  zip_name      = ""
  handler       = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest"
  function_name = "trello-specific-list"
  runtime       = "java8"
  memory_size   = 256

  environment {
    variables {
      key = "value"
    }
  }
}

module "trello-cards-sns-subscription" {
  source = "../../modules/sns-subscription"
  topic_arn                 = "${data.aws_sns_topic.trello-cards-sns.arn}"
  subscription_endpoint_arn = "${module.trello-cards-lambda.arn}"
}

module "trello-cars-processor-lambda" {
  source        = "../../modules/lambda"
  zip_name      = ""
  handler       = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest"
  function_name = "trello-specific-list"
  runtime       = "java8"
  memory_size   = 256

  environment {
    variables {
      key = "value"
    }
  }
}