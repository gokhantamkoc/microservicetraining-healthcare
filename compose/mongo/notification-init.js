db = db.getSiblingDB("notificationdb");
db.createUser({
  user: "notification_user",
  pwd: "notification_password",
  roles: [
    {
      role: "readWrite",
      db: "notificationdb"
    }
  ]
});

