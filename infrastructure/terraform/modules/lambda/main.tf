resource "aws_lambda_function" "lambda" {
  function_name = "${var.function_name}"
  description   = "${var.description}"
  filename         = "packages/${var.zip_name}.zip"
  role          = "${aws_iam_role.lambda.arn}"
  runtime       = "${var.runtime}"
  handler       = "${var.handler}"
  timeout       = "${var.timeout}"
  memory_size   = "${var.memory_size}"
  tags          = "${var.tags}"
  environment   = [
    "${slice(list(var.environment), 0, length(var.environment) == 0 ? 0 : 1 )}"]

  vpc_config {
    security_group_ids = ["${var.vpc_config["security_group_ids"]}"]
    subnet_ids         = ["${var.vpc_config["subnet_ids"]}"]
  }
}

resource "aws_iam_role" "assume_role_policy" {
  name = "iam_for_lambda"

  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Id": "default",
  "Statement": [
    {
      "Sid": "new-joiners-trelloCardsProcessorLambdaSNS1Permission-FH467TKWQH15",
      "Effect": "Allow",
      "Principal": {
        "Service": "sns.amazonaws.com"
      },
      "Action": "lambda:invokeFunction",
      "Resource": "arn:aws:lambda:eu-west-1:573623542714:function:new-joiners-trelloCardsProcessorLambda-1KKSEUNOYRST8",
      "Condition": {
        "ArnLike": {
          "AWS:SourceArn": "arn:aws:sns:eu-west-1:573623542714:new-joiners-trello-cards-processor"
        }
      }
    }
  ]
}
EOF
}

resource "aws_iam_role" "lambda" {
  name               = "${var.function_name}"
  assume_role_policy = "${aws_iam_role.assume_role_policy.arn}"
}

resource "aws_iam_role_policy_attachment" "vpc_attachment" {
  count = "${(length(var.vpc_config["security_group_ids"]) > 0 && length(var.vpc_config["subnet_ids"]) > 0) ? 1 : 0}"
  role  = "${aws_iam_role.lambda.name}"

  // see https://docs.aws.amazon.com/lambda/latest/dg/vpc.html
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaVPCAccessExecutionRole"
}