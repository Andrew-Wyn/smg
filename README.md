# CERERE

software per la generazione di automi a stati finiti (workflow), basato su spring boot e su spring state machine 

RUN:
```
make database (start mongodb daemon)
make run (run spring boot instance)
```

EXAMPLE CONFIGURATIONS:

- CD_PLAYER

```
{ 
    "initial" : "IDLE",
    "states" : [ 
      {
            "value" : "IDLE", 
            "type" : "NORMAL"
      }, 
      {
            "value" : "BUSY", 
            "type" : "NORMAL"
      }
    ], 
    
    "regions" : [ 
      {
        "parent" : "IDLE", 
        "initial" : "CLOSED",
        "states" : [ 
          {
            "value" : "CLOSED", 
            "type" : "NORMAL",
            "entryAction" : "closedEntryAction"
          }, 
          {
            "value" : "OPEN", 
            "type" : "NORMAL"
          }
        ]
      },
      {
        "parent" : "BUSY", 
        "initial" : "PLAYING",
        "states" : [ 
          {
            "value" : "PLAYING", 
            "type" : "NORMAL"
          }, 
          {
            "value" : "PAUSED", 
            "type" : "NORMAL"
          }
        ]
      }
    ],
    
    "transitions" : [ 
      { 
        "type" : "external",
        "source" : "CLOSED", 
        "target" : "OPEN", 
        "event" : "EJECT"
      },
  
      { 
        "type" : "external",
        "source" : "OPEN", 
        "target" : "CLOSED", 
        "event" : "EJECT"
      },
      
      { 
        "type" : "external",
        "source" : "OPEN", 
        "target" : "CLOSED", 
        "event" : "PLAY"
      },
      
      
      { 
        "type" : "external",
        "source" : "PLAYING", 
        "target" : "PAUSED", 
        "event" : "PAUSE"
      },
      
      { 
        "type" : "external",
        "source" : "PAUSED", 
        "target" : "PLAYING", 
        "event" : "PAUSE"
      },
      
      { 
        "type" : "external",
        "source" : "BUSY", 
        "target" : "IDLE", 
        "event" : "STOP"              
  
      },
      
      { 
        "type" : "external",
        "source" : "IDLE", 
        "target" : "BUSY", 
        "event" : "PLAY"
      },
      
      
      { 
        "type" : "internal",
        "source" : "PLAYING", 
        "timer" : 1000,
        "action" : "playingAction"
      },
      
      { 
        "type" : "internal",
        "source" : "PLAYING", 
        "event" : "BACK",
      "action" : "trackAction"
      },

      { 
        "type" : "internal",
        "source" : "PLAYING", 
        "event" : "FORWARD",
      "action" : "trackAction"
      },
      
      { 
        "type" : "internal",
        "source" : "OPEN", 
        "event" : "LOAD",
      "action" : "loadAction"
      }
    ], 
    "machineId" : "cdPlayer", 
    "autoStartUp" : true
  }
```


