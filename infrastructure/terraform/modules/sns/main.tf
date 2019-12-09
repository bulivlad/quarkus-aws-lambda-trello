resource "aws_sns_topic" "sns-topic" {
  name = "${var.name}"
}
