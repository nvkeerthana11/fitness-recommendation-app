import { Box, Button, FormControl, InputLabel, MenuItem, Select, TextField } from '@mui/material'
import React, { useState } from 'react'
import { addActivity } from './services/api'
import { blueGrey } from '@mui/material/colors';


const ActivityForm = ({ onActivityAdded }) => {

    const [activity, setActivity] = useState({
        type: "RUNNING", duration: '', caloriesBurned: '',
        additionalMetrics: {}
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await addActivity(activity);
            onActivityAdded();
            setActivity({ type: "RUNNING", duration: '', caloriesBurned: ''});
        } catch (error) {
            console.error(error);
        }
    }
    
  return (
    <Box component="form" onSubmit={handleSubmit}       
      

      
>
    <FormControl fullWidth sx={{mb: 2}}>
        <InputLabel>Activity Type</InputLabel>
        <Select
            value={activity.type}
            onChange={(e) => setActivity({...activity, type: e.target.value})}>
               <MenuItem value="RUNNING">Running</MenuItem>
<MenuItem value="WALKING">Walking</MenuItem>
<MenuItem value="CYCLING">Cycling</MenuItem>

<MenuItem value="SWIMMING">Swimming</MenuItem>
<MenuItem value="HIKING">Hiking</MenuItem>
<MenuItem value="YOGA">Yoga</MenuItem>
<MenuItem value="STRENGTH_TRAINING">Strength Training</MenuItem>
<MenuItem value="CARDIO">Cardio</MenuItem>
<MenuItem value="HIIT">HIIT</MenuItem>
<MenuItem value="ROWING">Rowing</MenuItem>
<MenuItem value="ELLIPTICAL">Elliptical</MenuItem>
<MenuItem value="PILATES">Pilates</MenuItem>
<MenuItem value="CROSSFIT">CrossFit</MenuItem>
<MenuItem value="DANCING">Dancing</MenuItem>
<MenuItem value="BOXING">Boxing</MenuItem>
<MenuItem value="CLIMBING">Climbing</MenuItem>
<MenuItem value="SKIPPING">Skipping Rope</MenuItem>
<MenuItem value="MARTIAL_ARTS">Martial Arts</MenuItem>
<MenuItem value="STRETCHING">Stretching</MenuItem>

            </Select>
    </FormControl>
    <TextField fullWidth
                label="Duration (Minutes)"
                type='number'
                sx={{ mb: 2}}
                value={activity.duration}
                onChange={(e) => setActivity({...activity, duration: e.target.value})}/>

<TextField fullWidth
                label="Calories Burned"
                type='number'
                sx={{ mb: 4}}
                value={activity.caloriesBurned}
                onChange={(e) => setActivity({...activity, caloriesBurned: e.target.value})}/>

<Button                  sx={{ mb: 4}} type='submit' variant='contained'>
    Add Activity
</Button>
  </Box>
  )
}

export default ActivityForm