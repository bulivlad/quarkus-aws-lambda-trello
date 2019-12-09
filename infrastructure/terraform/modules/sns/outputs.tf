output "sns-topic-arn" {
  description = "The Amazon Resource Name (ARN) identifying your SNS"
  value       = "${aws_sns_topic.sns-topic.arn}"
}

output "sns-topic-name" {
  description = "The Amazon Resource Name (ARN) identifying your SNS"
  value       = "${aws_sns_topic.sns-topic.name}"
}