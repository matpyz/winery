'use strict';

var dbm;
var type;
var seed;

/**
  * We receive the dbmigrate dependency from dbmigrate initially.
  * This enables us to not have to rely on NODE_PATH.
  */
exports.setup = function(options, seedLink) {
  dbm = options.dbmigrate;
  type = dbm.dataType;
  seed = seedLink;
};

exports.up = function(db) {
  db.createTable('user',{
    columns: {
      id: {
        type: 'int',
        unsigned: true,
        primaryKey: true,
        autoIncrement: true
      },
      name: 'string',
      email: 'string',
      password: 'string'
    },
    ifNotExists: true
  });
  
  db.createTable('group',{
    columns: {
      id: {
        type: 'int',
        unsigned: true,
        primaryKey: true,
        autoIncrement: true
      },
      name: 'string'
    },
    ifNotExists: true
  });

  db.createTable('permission',{
    columns: {
      id: {
        type: 'int',
        unsigned: true,
        primaryKey: true,
        autoIncrement: true
      },
      name: {
        type: 'string',
        notNull: true,
        unique: true
      }
    },
    ifNotExists: true
  });

  db.createTable('user2group',{
    columns: {
      userId: {
        type: 'int',
        unsigned: true,
        foreignKey: {
          name: 'user2group_user',
          table: 'user',
          rules: {
            onDelete: 'CASCADE',
            onUpdate: 'RESTRICT'
          },
          mapping: 'id'
        }
      },
      groupId: {
        type: 'int',
        unsigned: true,
        foreignKey: {
          name: 'user2group_group',
          table: 'group',
          rules: {
            onDelete: 'CASCADE',
            onUpdate: 'RESTRICT'
          },
          mapping: 'id'
        }
      },
    },
    ifNotExists: true
  });

  db.createTable('group2permission',{
    columns: {
      groupId: {
        type: 'int',
        unsigned: true,
        foreignKey: {
          name: 'group2permission_group',
          table: 'group',
          rules: {
            onDelete: 'CASCADE',
            onUpdate: 'RESTRICT'
          },
          mapping: 'id'
        }
      },
      permissionId: {
        type: 'int',
        unsigned: true,
        foreignKey: {
          name: 'group2permission_permission',
          table: 'permission',
          rules: {
            onDelete: 'CASCADE',
            onUpdate: 'RESTRICT'
          },
          mapping: 'id'
        }
      },
      access: 'int'
    },
    ifNotExists: true
  });
  return null;
};

exports.down = function(db) {
  db.dropTable('group2permission');
  db.dropTable('user2group');
  db.dropTable('permission');
  db.dropTable('group');
  db.dropTable('user');
  return null;
};
