// ============================================
// PRELIM LAB WORK 2 - ATTENDANCE TRACKER
// JavaScript Implementation
// ============================================

// Global variables
let attendanceRecords = [];
const CORRECT_USERNAME = "admin";
const CORRECT_PASSWORD = "password123";

// ============================================
// STEP 1: SETUP AND INITIALIZATION
// ============================================

/**
 * Initialize the application when DOM is fully loaded
 */
document.addEventListener('DOMContentLoaded', function() {
    console.log('Attendance Tracker initialized');
    
    // Set up event listeners
    setupEventListeners();
    
    // Start the clock
    updateClock();
    setInterval(updateClock, 1000);
});

/**
 * Set up all event listeners for forms and buttons
 */
function setupEventListeners() {
    // Login form submission
    const loginForm = document.getElementById('loginForm');
    loginForm.addEventListener('submit', handleLogin);
    
    // Attendance form submission
    const attendanceForm = document.getElementById('attendanceForm');
    attendanceForm.addEventListener('submit', handleAttendanceSubmit);
}

// ============================================
// STEP 2: LOGIN SYSTEM IMPLEMENTATION
// ============================================

/**
 * Handle login form submission
 * Validates username and password
 * Plays beep sound if incorrect
 * @param {Event} event - Form submit event
 */
function handleLogin(event) {
    event.preventDefault();
    
    // Get input values
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // Validate credentials
    if (username === CORRECT_USERNAME && password === CORRECT_PASSWORD) {
        // Successful login
        showSuccessLogin();
    } else {
        // Failed login - play beep sound
        playBeepSound();
        showLoginError('Invalid username or password!');
    }
}

/**
 * Show successful login and switch to attendance section
 */
function showSuccessLogin() {
    // Hide login section
    document.getElementById('loginSection').classList.add('hidden');
    
    // Show attendance section
    document.getElementById('attendanceSection').classList.remove('hidden');
    
    // Clear login form
    document.getElementById('loginForm').reset();
    
    // Hide any error messages
    document.getElementById('loginError').classList.add('hidden');
}

/**
 * Display login error message
 * @param {string} message - Error message to display
 */
function showLoginError(message) {
    const errorDiv = document.getElementById('loginError');
    errorDiv.textContent = message;
    errorDiv.classList.remove('hidden');
    
    // Hide error after 3 seconds
    setTimeout(() => {
        errorDiv.classList.add('hidden');
    }, 3000);
}

// ============================================
// STEP 3: BEEP SOUND GENERATION
// ============================================

/**
 * Play a beep sound using Web Audio API
 * This creates a 400Hz tone for 200ms
 * Plays when login credentials are incorrect
 */
function playBeepSound() {
    try {
        // Create audio context
        const audioContext = new (window.AudioContext || window.webkitAudioContext)();
        
        // Create oscillator (sound generator)
        const oscillator = audioContext.createOscillator();
        const gainNode = audioContext.createGain();
        
        // Connect oscillator to gain node to speakers
        oscillator.connect(gainNode);
        gainNode.connect(audioContext.destination);
        
        // Set beep properties
        oscillator.frequency.value = 800; // 800 Hz frequency
        oscillator.type = 'sine'; // Sine wave for smooth beep
        
        // Set volume
        gainNode.gain.value = 0.3; // 30% volume
        
        // Start and stop beep
        oscillator.start(audioContext.currentTime);
        oscillator.stop(audioContext.currentTime + 0.2); // 200ms duration
        
        console.log('Beep sound played');
    } catch (error) {
        console.error('Error playing beep sound:', error);
    }
}

// ============================================
// STEP 4: TIMESTAMP DISPLAY
// ============================================

/**
 * Update the current timestamp display
 * Runs every second to show real-time clock
 */
function updateClock() {
    const now = new Date();
    const formattedTime = formatDateTime(now);
    
    // Update the timestamp display
    const timestampElement = document.getElementById('currentTimestamp');
    if (timestampElement) {
        timestampElement.textContent = formattedTime;
    }
}

/**
 * Format date and time to readable string
 * Format: YYYY-MM-DD HH:MM:SS
 * @param {Date} date - Date object to format
 * @returns {string} Formatted date/time string
 */
function formatDateTime(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// ============================================
// STEP 5: ATTENDANCE RECORDING
// ============================================

/**
 * Handle attendance form submission
 * Records student attendance with timestamp and e-signature
 * @param {Event} event - Form submit event
 */
function handleAttendanceSubmit(event) {
    event.preventDefault();
    
    // Get form values
    const studentName = document.getElementById('studentName').value.trim();
    const courseYear = document.getElementById('courseYear').value.trim();
    
    // Validate inputs
    if (!studentName || !courseYear) {
        alert('Please fill in all required fields!');
        return;
    }
    
    // Create attendance record
    const attendanceRecord = {
        name: studentName,
        courseYear: courseYear,
        timeIn: formatDateTime(new Date()),
        signature: generateUUID()
    };
    
    // Add to records array
    attendanceRecords.push(attendanceRecord);
    
    // Update UI
    displayAttendanceRecord(attendanceRecord);
    updateSummary();
    showSuccessMessage(`Attendance recorded for ${studentName}!`);
    
    // Clear form and regenerate signature
    document.getElementById('attendanceForm').reset();
    generateNewSignature();
    
    console.log('Attendance recorded:', attendanceRecord);
}

/**
 * Generate a unique UUID for e-signature
 * Format: xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx
 * @returns {string} UUID string
 */
function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

/**
 * Generate new signature and timestamp for the form
 */
function generateNewSignature() {
    const signature = generateUUID();
    const timeIn = formatDateTime(new Date());
    
    document.getElementById('signature').value = signature;
    document.getElementById('timeIn').value = timeIn;
}

/**
 * Display attendance record in the list
 * @param {Object} record - Attendance record to display
 */
function displayAttendanceRecord(record) {
    const listContainer = document.getElementById('attendanceListContainer');
    const list = document.getElementById('attendanceList');
    
    // Show container if hidden
    listContainer.classList.remove('hidden');
    
    // Create attendance item
    const item = document.createElement('div');
    item.className = 'attendance-item';
    item.innerHTML = `
        <div class="attendance-item-name">${record.name}</div>
        <div class="attendance-item-details">
            ${record.courseYear} • ${record.timeIn}<br>
            Signature: ${record.signature}
        </div>
    `;
    
    // Add to list (newest first)
    list.insertBefore(item, list.firstChild);
}

/**
 * Show success message
 * @param {string} message - Success message to display
 */
function showSuccessMessage(message) {
    const successDiv = document.getElementById('successMessage');
    successDiv.textContent = message;
    successDiv.classList.remove('hidden');
    
    // Hide after 3 seconds
    setTimeout(() => {
        successDiv.classList.add('hidden');
    }, 3000);
}

// ============================================
// STEP 6: ATTENDANCE SUMMARY
// ============================================

/**
 * Update the attendance summary statistics
 */
function updateSummary() {
    if (attendanceRecords.length === 0) return;
    
    const summarySection = document.getElementById('summarySection');
    summarySection.classList.remove('hidden');
    
    // Total present
    document.getElementById('totalPresent').textContent = attendanceRecords.length;
    
    // First arrival (oldest record)
    const firstRecord = attendanceRecords[0];
    document.getElementById('firstArrival').textContent = firstRecord.timeIn;
    
    // Latest arrival (newest record)
    const latestRecord = attendanceRecords[attendanceRecords.length - 1];
    document.getElementById('latestArrival').textContent = latestRecord.timeIn;
}

// ============================================
// STEP 7: CSV FILE GENERATION
// ============================================

/**
 * Generate and download CSV file with attendance records
 * Creates a CSV file with all attendance data
 */
function generateCSV() {
    if (attendanceRecords.length === 0) {
        alert('No attendance records to export!');
        return;
    }
    
    // CSV Header
    let csvContent = 'Name,Course/Year,Time In,E-Signature\n';
    
    // Add each record
    attendanceRecords.forEach(record => {
        csvContent += `"${record.name}","${record.courseYear}","${record.timeIn}","${record.signature}"\n`;
    });
    
    // Create blob and download
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    
    // Create download link
    const url = URL.createObjectURL(blob);
    link.setAttribute('href', url);
    link.setAttribute('download', `attendance_${getDateString()}.csv`);
    link.style.visibility = 'hidden';
    
    // Trigger download
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    console.log('CSV file generated and downloaded');
}

/**
 * Get current date as string for filename
 * Format: YYYY-MM-DD
 * @returns {string} Date string
 */
function getDateString() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// ============================================
// ADDITIONAL FEATURES
// ============================================

/**
 * Logout and return to login screen
 */
function logout() {
    // Confirm logout
    if (confirm('Are you sure you want to logout?')) {
        // Hide attendance section
        document.getElementById('attendanceSection').classList.add('hidden');
        
        // Show login section
        document.getElementById('loginSection').classList.remove('hidden');
        
        // Clear attendance form
        document.getElementById('attendanceForm').reset();
        
        console.log('User logged out');
    }
}

// ============================================
// INITIALIZE FORM FIELDS ON LOAD
// ============================================

// Generate initial signature when page loads
window.addEventListener('load', function() {
    // Wait a bit for DOM to be ready
    setTimeout(() => {
        if (document.getElementById('signature')) {
            generateNewSignature();
        }
    }, 100);
});

// ============================================
// CONSOLE LOG FOR DEBUGGING
// ============================================

console.log('=================================');
console.log('ATTENDANCE TRACKER SYSTEM');
console.log('=================================');
console.log('Login Credentials:');
console.log('Username: admin');
console.log('Password: password123');
console.log('=================================');
