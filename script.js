document.addEventListener('DOMContentLoaded', function() {
    // --- Global User Data Storage ---
    let currentUser = {
        username: '',
        ic: '',
        gender: '', // 'male' or 'female'
        age: null,
        weight: null, // kg
        height: null, // cm
    };

    // --- User Information Section Elements ---
    const userInfoSection = document.getElementById('user-info-section');
    const healthDashboardContainer = document.getElementById('health-dashboard-container');
    const usernameInput = document.getElementById('username');
    const icNumberInput = document.getElementById('ic-number');
    const initialWeightInput = document.getElementById('initial-weight');
    const initialHeightInput = document.getElementById('initial-height');
    const submitUserInfoBtn = document.getElementById('submitUserInfo');
    const userInfoErrorDiv = document.getElementById('userInfoError');

    // --- Dashboard Elements for User Info Display ---
    const displayUsername = document.getElementById('displayUsername');
    const displayAge = document.getElementById('displayAge');
    const displayGender = document.getElementById('displayGender');
    const displayWeight = document.getElementById('displayWeight');
    const displayHeight = document.getElementById('displayHeight');
    const welcomeUsername = document.getElementById('welcomeUsername');
    const changeUserInfoBtn = document.getElementById('changeUserInfoBtn');


    // --- Helper Function: Calculate Age and Determine Gender from Malaysian IC ---
    // Format: YYMMDD-PB-#### (e.g., 900101-14-5678)
    function parseIC(ic) {
        const icRegex = /^(\d{2})(\d{2})(\d{2})-(\d{2})-(\d{3})(\d{1})$/;
        const match = ic.match(icRegex);

        if (!match) {
            return { error: "Invalid IC format. Please use YYMMDD-PB-####." };
        }

        const yearPart = parseInt(match[1], 10);
        const month = parseInt(match[2], 10);
        const day = parseInt(match[3], 10);
        const lastDigit = parseInt(match[6], 10);

        let year;
        // Determine century for birth year (simple logic, assumes current year is 2025)
        // If YY is <= current year's last two digits, assume 20YY, else 19YY
        const currentYearLastTwoDigits = new Date().getFullYear() % 100;
        if (yearPart <= currentYearLastTwoDigits) {
            year = 2000 + yearPart;
        } else {
            year = 1900 + yearPart;
        }

        const dob = new Date(year, month - 1, day); // Month is 0-indexed in Date constructor

        // Validate date
        if (isNaN(dob.getTime()) || dob.getMonth() !== month - 1 || dob.getDate() !== day) {
            return { error: "Invalid birth date in IC. Please check the YYMMDD." };
        }

        const today = new Date();
        let age = today.getFullYear() - dob.getFullYear();
        const m = today.getMonth() - dob.getMonth();
        if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
            age--;
        }

        const gender = (lastDigit % 2 === 0) ? 'female' : 'male';

        if (age < 0 || age > 120) { // Basic sanity check for age
            return { error: "Calculated age seems unrealistic. Please verify IC." };
        }

        return { age, gender, dob };
    }

    // --- Show User Info Form ---
    function showUserInfoForm() {
        userInfoSection.classList.remove('hidden');
        healthDashboardContainer.classList.add('hidden');
        // Reset values (optional, for re-entry)
        usernameInput.value = currentUser.username || '';
        icNumberInput.value = currentUser.ic || '';
        initialWeightInput.value = currentUser.weight || '';
        initialHeightInput.value = currentUser.height || '';
        userInfoErrorDiv.textContent = '';
    }

    // --- Show Dashboard and Populate Fields ---
    function showDashboard() {
        userInfoSection.classList.add('hidden');
        healthDashboardContainer.classList.remove('hidden');

        // Update sidebar user info
        displayUsername.textContent = currentUser.username;
        displayAge.textContent = currentUser.age;
        displayGender.textContent = currentUser.gender.charAt(0).toUpperCase() + currentUser.gender.slice(1);
        displayWeight.textContent = currentUser.weight;
        displayHeight.textContent = currentUser.height;
        welcomeUsername.textContent = currentUser.username;

        // Pre-fill health tool inputs
        // BMI
        document.getElementById('bmi-weight').value = currentUser.weight;
        document.getElementById('bmi-height').value = currentUser.height;

        // Body Fat
        document.getElementById('bf-gender').value = currentUser.gender;
        document.getElementById('bf-weight').value = currentUser.weight;
        document.getElementById('bf-height').value = currentUser.height;
        // Trigger change to hide/show hip for male/female
        document.getElementById('bf-gender').dispatchEvent(new Event('change'));

        // Calorie Needs
        document.getElementById('cal-gender').value = currentUser.gender;
        document.getElementById('cal-age').value = currentUser.age;
        document.getElementById('cal-weight').value = currentUser.weight;
        document.getElementById('cal-height').value = currentUser.height;

        // IBW
        document.getElementById('ibw-gender').value = currentUser.gender;
        document.getElementById('ibw-height').value = currentUser.height;

        // Water Intake
        document.getElementById('water-weight').value = currentUser.weight;

        // Automatically show dashboard section (default)
        showSection('dashboard-section');
        const defaultNavLink = document.querySelector('.nav-link[data-target="dashboard-section"]');
        if (defaultNavLink) {
            navLinks.forEach(link => link.classList.remove('active'));
            defaultNavLink.classList.add('active');
        }
    }

    // --- Event Listener for User Info Submission ---
    if (submitUserInfoBtn) {
        submitUserInfoBtn.addEventListener('click', function() {
            const username = usernameInput.value.trim();
            const icNumber = icNumberInput.value.trim();
            const weight = parseFloat(initialWeightInput.value);
            const height = parseFloat(initialHeightInput.value);

            if (!username || !icNumber || isNaN(weight) || isNaN(height) || weight <= 0 || height <= 0) {
                userInfoErrorDiv.innerHTML = "Please fill in all fields with valid positive numbers for weight and height.";
                userInfoErrorDiv.style.color = 'red';
                return;
            }

            const icParseResult = parseIC(icNumber);
            if (icParseResult.error) {
                userInfoErrorDiv.innerHTML = icParseResult.error;
                userInfoErrorDiv.style.color = 'red';
                return;
            }

            // All validations passed, store data and show dashboard
            currentUser.username = username;
            currentUser.ic = icNumber;
            currentUser.age = icParseResult.age;
            currentUser.gender = icParseResult.gender;
            currentUser.weight = weight;
            currentUser.height = height;

            userInfoErrorDiv.textContent = ''; // Clear any previous errors
            showDashboard();
        });
    }

    // --- Event Listener for "Change Info" Button in Sidebar ---
    if (changeUserInfoBtn) {
        changeUserInfoBtn.addEventListener('click', function() {
            showUserInfoForm();
        });
    }

    // --- Sidebar Toggle Functionality (from previous code) ---
    const toggleSidebarBtn = document.getElementById('toggleSidebarBtn');
    const sidebar = document.getElementById('sidebar');

    if (toggleSidebarBtn && sidebar) {
        toggleSidebarBtn.addEventListener('click', function() {
            sidebar.classList.toggle('collapsed');
        });

        function handleSidebarResponsiveness() {
            if (window.innerWidth <= 992) {
                sidebar.classList.add('collapsed');
            } else {
                sidebar.classList.remove('collapsed');
            }
            if (window.innerWidth <= 576) {
                sidebar.classList.remove('collapsed');
            }
        }
        // No initial check on load, let showUserInfoForm handle visibility initially
        window.addEventListener('resize', handleSidebarResponsiveness);
    }

    // --- Section Navigation Functionality (from previous code) ---
    const navLinks = document.querySelectorAll('.nav-link');
    const toolSections = document.querySelectorAll('.tool-section');
    const currentToolTitle = document.getElementById('currentToolTitle');

    function showSection(targetId) {
        toolSections.forEach(section => {
            section.classList.remove('active');
        });
        const activeSection = document.getElementById(targetId);
        if (activeSection) {
            activeSection.classList.add('active');
            const sectionTitle = activeSection.querySelector('h2');
            if (sectionTitle) {
                currentToolTitle.textContent = sectionTitle.textContent;
            } else {
                currentToolTitle.textContent = "Dashboard Overview";
            }
        }
    }

    navLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();

            if (healthDashboardContainer.classList.contains('hidden')) {
                // Prevent navigation if dashboard is not active (user info not entered)
                return;
            }

            navLinks.forEach(item => item.classList.remove('active'));
            this.classList.add('active');

            const targetSectionId = this.dataset.target;
            showSection(targetSectionId);
            window.history.pushState(null, '', `#${targetSectionId.replace('-section', '')}`);
        });
    });

    window.addEventListener('popstate', function() {
        if (!healthDashboardContainer.classList.contains('hidden')) { // Only handle if dashboard is visible
            const hash = window.location.hash.substring(1);
            if (hash) {
                showSection(hash + '-section');
                navLinks.forEach(link => {
                    link.classList.remove('active');
                    if (link.dataset.target === hash + '-section') {
                        link.classList.add('active');
                    }
                });
            } else {
                showSection('dashboard-section');
                navLinks.forEach(link => {
                    link.classList.remove('active');
                    if (link.dataset.target === 'dashboard-section') {
                        link.classList.add('active');
                    }
                });
            }
        }
    });

    // --- BMI Calculator Logic ---
    const bmiWeightInput = document.getElementById('bmi-weight');
    const bmiHeightInput = document.getElementById('bmi-height');
    const calculateBmiBtn = document.getElementById('calculateBmi');
    const bmiResultDiv = document.getElementById('bmiResult');

    if (calculateBmiBtn) {
        calculateBmiBtn.addEventListener('click', function() {
            const weightKg = parseFloat(bmiWeightInput.value);
            const heightCm = parseFloat(bmiHeightInput.value);

            if (isNaN(weightKg) || isNaN(heightCm) || weightKg <= 0 || heightCm <= 0) {
                bmiResultDiv.innerHTML = "Please enter valid positive numbers for weight and height.";
                bmiResultDiv.style.color = 'red';
                return;
            }

            const heightM = heightCm / 100;
            const bmi = weightKg / (heightM * heightM);
            let category = '';

            if (bmi < 18.5) {
                category = 'Underweight';
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                category = 'Normal weight';
            } else if (bmi >= 25 && bmi <= 29.9) {
                category = 'Overweight';
            } else {
                category = 'Obesity';
            }

            bmiResultDiv.innerHTML = `Your BMI is: <strong>${bmi.toFixed(2)}</strong><br>Category: <strong>${category}</strong>`;
            bmiResultDiv.style.color = '#0056b3';
        });
    }

    // --- Body Fat % Calculator Logic ---
    const bfGenderSelect = document.getElementById('bf-gender');
    const bfWeightInput = document.getElementById('bf-weight');
    const bfHeightInput = document.getElementById('bf-height');
    const bfNeckInput = document.getElementById('bf-neck');
    const bfWaistInput = document.getElementById('bf-waist');
    const bfHipInput = document.getElementById('bf-hip');
    const bfHipGroup = document.getElementById('bf-hip-group');
    const calculateBodyFatBtn = document.getElementById('calculateBodyFat');
    const bodyFatResultDiv = document.getElementById('bodyFatResult');

    if (bfGenderSelect) {
        bfGenderSelect.addEventListener('change', function() {
            if (this.value === 'male') {
                bfHipGroup.style.display = 'none';
                bfHipInput.value = '';
            } else {
                bfHipGroup.style.display = 'block';
            }
        });
        // Trigger change on load if an initial value is set by pre-fill
        // bfGenderSelect.dispatchEvent(new Event('change')); // Uncomment if you want to trigger on initial load based on pre-fill
    }

    if (calculateBodyFatBtn) {
        calculateBodyFatBtn.addEventListener('click', function() {
            const gender = bfGenderSelect.value;
            const weightKg = parseFloat(bfWeightInput.value);
            const heightCm = parseFloat(bfHeightInput.value);
            const neckCm = parseFloat(bfNeckInput.value);
            const waistCm = parseFloat(bfWaistInput.value);
            let hipCm = 0;

            if (gender === 'female') {
                hipCm = parseFloat(bfHipInput.value);
            }

            if (isNaN(weightKg) || isNaN(heightCm) || isNaN(neckCm) || isNaN(waistCm) ||
                weightKg <= 0 || heightCm <= 0 || neckCm <= 0 || waistCm <= 0 ||
                (gender === 'female' && (isNaN(hipCm) || hipCm <= 0))) {
                bodyFatResultDiv.innerHTML = "Please enter valid positive numbers for all measurements.";
                bodyFatResultDiv.style.color = 'red';
                return;
            }

            let bodyFatPercentage;
            const heightInches = heightCm / 2.54;
            const neckInches = neckCm / 2.54;
            const waistInches = waistCm / 2.54;
            const hipInches = hipCm / 2.54;

            if (gender === 'male') {
                bodyFatPercentage = 495 / (1.0324 - 0.19077 * Math.log10(waistInches - neckInches) + 0.15456 * Math.log10(heightInches)) - 450;
            } else {
                bodyFatPercentage = 495 / (1.29579 - 0.35004 * Math.log10(waistInches + hipInches - neckInches) + 0.22100 * Math.log10(heightInches)) - 450;
            }

            if (isNaN(bodyFatPercentage) || bodyFatPercentage < 0 || bodyFatPercentage > 100) {
                bodyFatResultDiv.innerHTML = "Could not calculate. Please double-check your measurements.";
                bodyFatResultDiv.style.color = 'red';
                return;
            }

            let category = '';
            if (gender === 'male') {
                if (bodyFatPercentage <= 6) category = 'Essential Fat';
                else if (bodyFatPercentage <= 13) category = 'Athletes';
                else if (bodyFatPercentage <= 17) category = 'Fitness';
                else if (bodyFatPercentage <= 24) category = 'Acceptable';
                else category = 'Obese';
            } else {
                if (bodyFatPercentage <= 13) category = 'Essential Fat';
                else if (bodyFatPercentage <= 20) category = 'Athletes';
                else if (bodyFatPercentage <= 24) category = 'Fitness';
                else if (bodyFatPercentage <= 31) category = 'Acceptable';
                else category = 'Obese';
            }

            bodyFatResultDiv.innerHTML = `Your Body Fat % is: <strong>${bodyFatPercentage.toFixed(2)}%</strong><br>Category: <strong>${category}</strong>`;
            bodyFatResultDiv.style.color = '#0056b3';
        });
    }

    // --- Calorie Needs Calculator Logic ---
    const calGenderSelect = document.getElementById('cal-gender');
    const calAgeInput = document.getElementById('cal-age');
    const calWeightInput = document.getElementById('cal-weight');
    const calHeightInput = document.getElementById('cal-height');
    const calActivitySelect = document.getElementById('cal-activity');
    const calculateCaloriesBtn = document.getElementById('calculateCalories');
    const calorieResultDiv = document.getElementById('calorieResult');

    if (calculateCaloriesBtn) {
        calculateCaloriesBtn.addEventListener('click', function() {
            const gender = calGenderSelect.value;
            const age = parseFloat(calAgeInput.value);
            const weightKg = parseFloat(calWeightInput.value);
            const heightCm = parseFloat(calHeightInput.value);
            const activityFactor = parseFloat(calActivitySelect.value);

            if (isNaN(age) || isNaN(weightKg) || isNaN(heightCm) || age <= 0 || weightKg <= 0 || heightCm <= 0) {
                calorieResultDiv.innerHTML = "Please enter valid positive numbers for age, weight, and height.";
                calorieResultDiv.style.color = 'red';
                return;
            }

            let bmr;
            if (gender === 'male') {
                bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) + 5;
            } else {
                bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age) - 161;
            }

            const tdee = bmr * activityFactor;

            calorieResultDiv.innerHTML = `Estimated BMR: <strong>${bmr.toFixed(0)} calories/day</strong><br>
                                            Estimated Daily Calorie Needs (TDEE): <strong>${tdee.toFixed(0)} calories/day</strong>`;
            calorieResultDiv.style.color = '#0056b3';
        });
    }

    // --- Ideal Body Weight (IBW) Calculator Logic ---
    const ibwGenderSelect = document.getElementById('ibw-gender');
    const ibwHeightInput = document.getElementById('ibw-height');
    const calculateIbwBtn = document.getElementById('calculateIbw');
    const ibwResultDiv = document.getElementById('ibwResult');

    if (calculateIbwBtn) {
        calculateIbwBtn.addEventListener('click', function() {
            const gender = ibwGenderSelect.value;
            const heightCm = parseFloat(ibwHeightInput.value);

            if (isNaN(heightCm) || heightCm <= 0) {
                ibwResultDiv.innerHTML = "Please enter a valid positive number for height.";
                ibwResultDiv.style.color = 'red';
                return;
            }

            const heightInches = heightCm / 2.54;

            let ibwResults = {};

            if (gender === 'male') {
                ibwResults.devine = 50 + 2.3 * Math.max(0, heightInches - 60);
                ibwResults.robinson = 52 + 1.9 * Math.max(0, heightInches - 60);
                ibwResults.miller = 56.2 + 1.41 * Math.max(0, heightInches - 60);
                ibwResults.hamwi = 48 + 2.7 * Math.max(0, heightInches - 60);
            } else {
                ibwResults.devine = 45.5 + 2.3 * Math.max(0, heightInches - 60);
                ibwResults.robinson = 49 + 1.7 * Math.max(0, heightInches - 60);
                ibwResults.miller = 53.1 + 1.36 * Math.max(0, heightInches - 60);
                ibwResults.hamwi = 45.5 + 2.2 * Math.max(0, heightInches - 60);
            }

            let resultHtml = `Your height: <strong>${heightCm} cm (${heightInches.toFixed(1)} inches)</strong><br><br>
                                Ideal Body Weight (IBW) estimates:<br>
                                <ul>
                                    <li>Devine: <strong>${ibwResults.devine.toFixed(1)} kg</strong></li>
                                    <li>Robinson: <strong>${ibwResults.robinson.toFixed(1)} kg</strong></li>
                                    <li>Miller: <strong>${ibwResults.miller.toFixed(1)} kg</strong></li>
                                    <li>Hamwi: <strong>${ibwResults.hamwi.toFixed(1)} kg</strong></li>
                                </ul>
                                <small>Note: These are estimates based on different formulas. Consult a healthcare professional for personalized advice.</small>`;
            ibwResultDiv.innerHTML = resultHtml;
            ibwResultDiv.style.color = '#0056b3';
        });
    }

    // --- Water Intake Calculator Logic ---
    const waterWeightInput = document.getElementById('water-weight');
    const waterActivitySelect = document.getElementById('water-activity');
    const calculateWaterBtn = document.getElementById('calculateWater');
    const waterResultDiv = document.getElementById('waterResult');

    if (calculateWaterBtn) {
        calculateWaterBtn.addEventListener('click', function() {
            const weightKg = parseFloat(waterWeightInput.value);
            const activityLevel = waterActivitySelect.value;

            if (isNaN(weightKg) || weightKg <= 0) {
                waterResultDiv.innerHTML = "Please enter a valid positive number for weight.";
                waterResultDiv.style.color = 'red';
                return;
            }

            let waterOunces = (weightKg * 2.20462) * (2/3);

            if (activityLevel === 'moderate') {
                waterOunces += 12;
            } else if (activityLevel === 'high') {
                waterOunces += 24;
            }

            const waterLiters = waterOunces * 0.0295735;

            waterResultDiv.innerHTML = `Recommended Daily Water Intake:<br>
                                        <strong>${waterLiters.toFixed(2)} Liters</strong> (approx. ${waterOunces.toFixed(0)} ounces)`;
            waterResultDiv.style.color = '#0056b3';
        });
    }

    // --- Initial Load: Show user info form ---
    showUserInfoForm();
    // After the initial form is submitted and dashboard is shown, then
    // the sidebar responsiveness listener will also apply.
    // Call it here to set initial collapsed state if window is small
    handleSidebarResponsiveness(); // Ensure sidebar starts collapsed on small screens if applicable
});