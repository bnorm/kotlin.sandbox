workflow "main" {
  on = "push"
  resolves = ["Build"]
}

action "Build" {
  uses = "MrRamych/gradle-actions@master"
  args = "build"
}
