db = db.getSiblingDB("tenantdb");
db.createUser({
  user: "tenant_user",
  pwd: "tenant_password",
  roles: [
    {
      role: "readWrite",
      db: "tenantdb"
    }
  ]
});

