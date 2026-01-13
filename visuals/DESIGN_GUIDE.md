# 📐 Design System - Guide de Développement

## Installation & Configuration

### Fonts (Poppins & Inter)

Les fonts utilisées dans le design sont déclarées comme XML, mais pour une meilleure intégration :

1. **Télécharger les fonts**:
   - Poppins: https://fonts.google.com/specimen/Poppins
   - Inter: https://fonts.google.com/specimen/Inter

2. **Placer dans** `res/font/`:
   ```
   res/font/
   ├── poppins_bold.ttf
   ├── poppins_semibold.ttf
   ├── inter_regular.ttf
   └── (fichiers XML déjà créés)
   ```

3. **Alternative**: Utiliser les system fonts
   ```xml
   android:fontFamily="sans-serif"  <!-- Inter-like -->
   android:fontFamily="sans-serif-medium"  <!-- Poppins-like -->
   ```

---

## 🎨 Utilisation des Couleurs

### Accès aux couleurs

```kotlin
// Dans le code Java/Kotlin
int primaryColor = ContextCompat.getColor(context, R.color.primary);
int accentColor = ContextCompat.getColor(context, R.color.accent);
```

### Gradient dans les layouts

```xml
<View
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:background="@drawable/gradient_blue_accent" />
```

### Status Colors

```xml
<!-- Success: #10B981 -->
<!-- Warning: #F59E0B -->
<!-- Error: #EF4444 -->
<!-- Info: #3B82F6 -->
```

---

## 📏 Système d'Espacement

### Utilisation des dimens

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="@dimen/spacing_lg"
    android:spacing="@dimen/spacing_md">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="@dimen/spacing_md" />
</LinearLayout>
```

### Cheatsheet des spacings

| Variable | Valeur | Usage |
|----------|--------|-------|
| spacing_xs | 4dp | Minimal gaps |
| spacing_sm | 8dp | Small spacing |
| spacing_md | 12dp | Medium items |
| spacing_lg | 16dp | Main padding |
| spacing_xl | 24dp | Large sections |
| spacing_xxl | 32dp | Top-level sections |

---

## 🔤 Typographie

### Style Text avec classes

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    style="@style/Typography.Heading.Large"
    android:text="Heading Text" />

<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    style="@style/Typography.Body.Medium"
    android:text="Body text" />
```

### Styles disponibles

```
Typography.Display.Large      (32sp, Bold)
Typography.Display.Medium     (28sp, Semibold)
Typography.Heading.Large      (24sp, Bold)
Typography.Heading.Medium     (20sp, Semibold)
Typography.Heading.Small      (16sp, Semibold)
Typography.Body.Large         (16sp, Regular, 1.5 line height)
Typography.Body.Medium        (14sp, Regular)
Typography.Body.Small         (12sp, Regular)
Typography.Label              (12sp, Semibold, Uppercase)
```

---

## 🎯 Composants Material3

### Buttons Premium

```xml
<com.google.android.material.button.MaterialButton
    android:layout_width="match_parent"
    android:layout_height="@dimen/button_height"
    style="@style/Button.Premium"
    android:text="Click Me"
    android:backgroundTint="@color/primary" />
```

### Text Input

```xml
<com.google.android.material.textfield.TextInputLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    style="@style/TextInput.Premium"
    android:hint="Input Hint">

    <com.google.android.material.textfield.TextInputEditText
        android:layout_width="match_parent"
        android:layout_height="@dimen/input_height"
        android:inputType="text" />
</com.google.android.material.textfield.TextInputLayout>
```

### Cards

```xml
<com.google.android.material.card.MaterialCardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardCornerRadius="@dimen/radius_lg"
    app:cardElevation="4dp"
    app:strokeColor="@color/gray_200"
    app:strokeWidth="1dp">
    <!-- Content -->
</com.google.android.material.card.MaterialCardView>
```

---

## 🌙 Dark Mode Implementation

### Colors automatiques

Les fichiers `values-night/colors.xml` s'appliquent automatiquement en dark mode.

### Vérifier le mode courant

```kotlin
val isDarkMode = (context.resources.configuration.uiMode and 
                 Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
```

### Forcer un mode

```kotlin
// Light mode
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

// Dark mode
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

// System default
AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
```

---

## ✨ Animations Recommandées

### Page Load Animation

```kotlin
// Fragment création
val view = inflater.inflate(R.layout.fragment_layout, container, false)
view.alpha = 0f
view.animate()
    .alpha(1f)
    .setDuration(300)
    .start()
```

### Staggered List Animation

```kotlin
val adapter = MyAdapter().apply {
    setHasStableIds(true)
}
val layoutAnimationController = LayoutAnimationController(
    AnimationUtils.loadAnimation(context, R.anim.slide_up),
    0.15f
)
recyclerView.layoutAnimation = layoutAnimationController
```

### Button Click Ripple

```xml
android:foreground="?attr/selectableItemBackground"
android:clickable="true"
android:focusable="true"
```

---

## 🔧 Troubleshooting

### Fonts non applicables
**Solution**: Utiliser `android:fontFamily="@font/inter_regular"` au lieu de `android:typeface`

### Gradient ne s'affiche pas
**Solution**: Vérifier que le drawable est en `.xml` format et utilisé via `android:background`

### Colors-night ne s'appliquent pas
**Solution**: Redémarrer l'appli ou utiliser "Force night mode" en developer settings

### Material3 Components manquants
**Solution**: Vérifier la version de material3 dans `build.gradle`:
```gradle
implementation "com.google.android.material:material:1.10.0"
```

---

## 📱 Responsive Design

### Landscape adjustments
Créer `res/layout-land/fragment_*.xml` pour les layouts landscape.

### Tablet optimizations
Créer `res/layout-sw600dp/` pour les écrans larges (tablets).

### Exemple tablet product grid
```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/rvProducts"
    app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
    app:spanCount="3" /> <!-- 3 colonnes au lieu de 2 -->
```

---

## ✅ Checklist pour Contributeurs

- [ ] Utiliser le système d'espacement (dimens.xml)
- [ ] Respecter la palette de couleurs
- [ ] Utiliser les styles Typography pour le texte
- [ ] Arrondir les coins (min 8dp)
- [ ] Utiliser Material3 components
- [ ] Tester en dark mode
- [ ] Vérifier l'accessibility (contraste, labels)
- [ ] Tester sur écrans small et large
- [ ] Ajouter des micro-interactions où pertinent

---

## 🎓 Ressources

- [Material Design 3 Guidelines](https://m3.material.io/)
- [Android Material Components](https://github.com/material-components/material-components-android)
- [Google Fonts](https://fonts.google.com/)

---

Happy Designing! 🚀
